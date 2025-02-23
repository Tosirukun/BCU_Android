package com.mandarin.bcu.androidutil.enemy.coroutine

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mandarin.bcu.R
import com.mandarin.bcu.androidutil.Definer
import com.mandarin.bcu.androidutil.StaticStore
import com.mandarin.bcu.androidutil.enemy.adapters.DynamicEmExplanation
import com.mandarin.bcu.androidutil.enemy.adapters.EnemyRecycle
import com.mandarin.bcu.androidutil.supports.CoroutineTask
import common.pack.Identifier
import common.util.lang.MultiLangCont
import common.util.unit.AbEnemy
import common.util.unit.Enemy
import java.lang.ref.WeakReference

class EInfoLoader : CoroutineTask<String> {
    private val weakReference: WeakReference<Activity>
    private val data: Identifier<AbEnemy>
    private var multi = -1
    private var amulti = -1

    constructor(activity: Activity, data: Identifier<AbEnemy>) : super() {
        weakReference = WeakReference(activity)
        this.data = data
    }

    constructor(activity: Activity, multi: Int, amulti: Int, data: Identifier<AbEnemy>) : super() {
        weakReference = WeakReference(activity)
        this.multi = multi
        this.amulti = amulti
        this.data = data
    }

    private val done = "done"

    override fun prepare() {
        val activity = weakReference.get() ?: return

        val e = data.get() ?: return

        if(e !is Enemy)
            return

        if (MultiLangCont.getStatic().EEXP.getCont(e) == null && (e.id.pack == Identifier.DEF || e.desc.isBlank())) {
            val view1 = activity.findViewById<View>(R.id.enemviewtop)
            val view2 = activity.findViewById<View>(R.id.enemviewbot)
            val viewPager: ViewPager = activity.findViewById(R.id.eneminfexp)
            val exptext = activity.findViewById<TextView>(R.id.eneminfexptx)
            val eanim = activity.findViewById<Button>(R.id.eanimanim)

            if (view1 != null) {
                view1.visibility = View.GONE
                view2.visibility = View.GONE
                viewPager.visibility = View.GONE
                exptext.visibility = View.GONE
                eanim.visibility = View.GONE
            }
        }
    }

    override fun doSomething() {
        val activity = weakReference.get() ?: return

        Definer.define(activity, this::updateProg, this::updateText)

        publishProgress(done)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun progressUpdate(vararg data: String) {
        val activity = weakReference.get() ?: return

        when(data[0]) {
            StaticStore.TEXT -> {
                val status = activity.findViewById<TextView>(R.id.status)

                status.text = data[1]
            }
            StaticStore.PROG -> {
                val prog = activity.findViewById<ProgressBar>(R.id.prog)

                if(data[1].toInt() == -1) {
                    prog.isIndeterminate = true

                    return
                }

                prog.isIndeterminate = false
                prog.max = 10000
                prog.progress = data[1].toInt()
            }
            done -> {
                val prog = activity.findViewById<ProgressBar>(R.id.prog)

                prog.isIndeterminate = true

                val recyclerView: RecyclerView = activity.findViewById(R.id.eneminftable)

                val enemyRecycle: EnemyRecycle = if (multi != -1 && amulti != -1)
                    EnemyRecycle(activity, multi, amulti, this.data)
                else
                    EnemyRecycle(activity, this.data)

                recyclerView.layoutManager = LinearLayoutManager(activity)
                recyclerView.adapter = enemyRecycle

                ViewCompat.setNestedScrollingEnabled(recyclerView, false)

                val explain = DynamicEmExplanation(activity, this.data)

                val viewPager: ViewPager = activity.findViewById(R.id.eneminfexp)

                viewPager.adapter = explain
                viewPager.offscreenPageLimit = 1

                val treasure: FloatingActionButton = activity.findViewById(R.id.enemtreasure)
                val main: ConstraintLayout = activity.findViewById(R.id.enemmainlayout)
                val treasurelay: ConstraintLayout = activity.findViewById(R.id.enemtreasuretab)

                val set = AnimatorSet()

                treasure.setOnClickListener {
                    if (!StaticStore.EisOpen) {
                        val slider = ValueAnimator.ofInt(0, treasurelay.width).setDuration(300)
                        slider.addUpdateListener { animation ->
                            treasurelay.translationX = -(animation.animatedValue as Int).toFloat()
                            treasurelay.requestLayout()
                        }
                        set.play(slider)
                        set.interpolator = DecelerateInterpolator()
                        set.start()
                        StaticStore.EisOpen = true
                    } else {
                        val view = activity.currentFocus
                        if (view != null) {
                            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view.windowToken, 0)
                            treasurelay.clearFocus()
                        }
                        val slider = ValueAnimator.ofInt(treasurelay.width, 0).setDuration(300)
                        slider.addUpdateListener { animation ->
                            treasurelay.translationX = -(animation.animatedValue as Int).toFloat()
                            treasurelay.requestLayout()
                        }
                        set.play(slider)
                        set.interpolator = AccelerateInterpolator()
                        set.start()
                        StaticStore.EisOpen = false
                    }
                }

                treasurelay.setOnTouchListener { _, _ ->
                    main.isClickable = false
                    true
                }
            }
        }
    }

    override fun finish() {
        val activity = weakReference.get() ?: return

        val back: FloatingActionButton = activity.findViewById(R.id.eneminfbck)
        val eanim = activity.findViewById<Button>(R.id.eanimanim)
        val treasurelay: ConstraintLayout = activity.findViewById(R.id.enemtreasuretab)

        if(StaticStore.EisOpen) {
            treasurelay.translationX = -treasurelay.width.toFloat()
            treasurelay.requestLayout()
        }

        back.setOnClickListener {
            StaticStore.EisOpen = false
            activity.finish()
        }
        val scrollView = activity.findViewById<ScrollView>(R.id.eneminfscroll)
        val prog = activity.findViewById<ProgressBar>(R.id.prog)
        val st = activity.findViewById<TextView>(R.id.status)

        scrollView.visibility = View.VISIBLE
        eanim.visibility = View.VISIBLE
        prog.visibility = View.GONE
        st.visibility = View.GONE
    }

    private fun updateText(info: String) {
        val ac = weakReference.get() ?: return

        publishProgress(StaticStore.TEXT, StaticStore.getLoadingText(ac, info))
    }

    private fun updateProg(p: Double) {
        publishProgress(StaticStore.PROG, (p * 10000.0).toInt().toString())
    }
}
package com.example.qqstepview

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator

class MainActivity : AppCompatActivity() {
    private lateinit var stepView: StepView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stepView = findViewById(R.id.qqStepView)
    }

    override fun onResume() {
        super.onResume()
        stepView.setStepMax(4000)
        val animator = ObjectAnimator.ofInt(stepView, "currentStep", 0, 3000)
        animator.duration = 2000
        animator.interpolator = FastOutSlowInInterpolator()
        animator.start()
    }
}

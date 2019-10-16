package com.my_project.airanimation.utilities

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import com.my_project.airanimation.data.entities.Loc
import java.math.BigInteger
import kotlin.math.pow

/**
 * Created Максим on 12.10.2019.
 * Copyright © Max
 */

fun getPoints(from: Loc, to: Loc): Array<Array<Double>> {
    return when {
        from.lat < to.lat
                && from.lon < to.lon
                || from.lat > to.lat
                && from.lon > to.lon -> {
            arrayOf(
                arrayOf(from.lat, from.lon),
                arrayOf(to.lat, from.lon),
                arrayOf(from.lat, to.lon),
                arrayOf(to.lat, to.lon)
            )
        }
        else -> arrayOf(
            arrayOf(from.lat, from.lon),
            arrayOf(from.lat, to.lon),
            arrayOf(to.lat, from.lon),
            arrayOf(to.lat, to.lon)
        )
    }
}

fun getDotsForRoute(xy: Array<Array<Double>>): List<Loc> {
    val dots = mutableListOf<Loc>()
    var t = 0.0
    val count = 35.0
    for (i in 0 until count.toInt()) {
        val b = t
        val a = 1 - b

        var nextX = 0.0
        var nextY = 0.0

        val s = xy.size
        for (j in 0 until s) {
            val rate = getRate(s - 1, j)
            nextX += rate * a.pow(s - 1 - j) * b.pow(j) * xy[j][0]
            nextY += rate * a.pow(s - 1 - j) * b.pow(j) * xy[j][1]
        }

        dots.add(Loc(nextX, nextY))
        t += (1.0 / count)
    }
    return dots
}

fun getRate(n: Int, k: Int): Int {
    return (factorial(n) / (factorial(k) * factorial(n - k))).toInt()
}

fun factorial(n: Int): BigInteger {
    if (n == 0) return BigInteger.ONE
    return BigInteger.valueOf(n.toLong()) * factorial(n - 1)
}

fun getAircraftAnimator(
    xy: Array<Array<Double>>,
    action: (item: Loc, angle: Float) -> Unit
): Animator {
    var currentX = 0.0
    var currentY = 0.0
    val animator = ValueAnimator.ofFloat(0f, 1f)
    animator.duration = 25000
    animator.interpolator = AccelerateDecelerateInterpolator()
    animator.addUpdateListener {
        val b = it.animatedValue as Float
        val a = 1 - b

        var nextX = 0.0
        var nextY = 0.0

        val s = xy.size
        for (i in 0 until s) {
            val coef = getRate(s - 1, i)
            nextX += coef * a.pow(s - 1 - i) * b.pow(i) * xy[i][0]
            nextY += coef * a.pow(s - 1 - i) * b.pow(i) * xy[i][1]
        }

        val angle = when {
            nextX > currentX && nextY > currentY -> {
                Math.toDegrees(Math.atan(((nextY - currentY) / (nextX - currentX)))) - 90
            }
            nextX > currentX && nextY < currentY -> {
                Math.toDegrees(Math.atan(((nextX - currentX) / (currentY - nextY)))) - 180
            }
            nextX < currentX && nextY > currentY -> {
                Math.toDegrees(Math.atan(((currentX - nextX) / (nextY - currentY))))
            }
            else -> {
                Math.toDegrees(Math.atan(((currentY - nextY) / (currentX - nextX)))) + 90
            }
        }

        currentX = nextX
        currentY = nextY

        action(Loc(nextX, nextY), angle.toFloat())
    }
    return animator
}


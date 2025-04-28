package com.tbc.bookli.presentation.screen.introduction

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.tbc.bookli.R

data class IntroductionSlide(
    @RawRes val animationResId: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
)

internal val introductionSlides = listOf(
    IntroductionSlide(
        animationResId = R.raw.first_slide,
        title = R.string.slide_title_explore_books,
        description = R.string.slide_description_explore_books
    ),
    IntroductionSlide(
        animationResId = R.raw.second_slide,
        title = R.string.slide_title_read_anytime,
        description = R.string.slide_description_read_anytime
    ),
    IntroductionSlide(
        animationResId = R.raw.third_slide,
        title = R.string.slide_title_build_library,
        description = R.string.slide_description_build_library
    )
)
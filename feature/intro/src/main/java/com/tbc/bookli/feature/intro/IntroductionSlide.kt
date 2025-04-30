package com.tbc.bookli.feature.intro

import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.tbc.bookli.core.common.R as CommonR


data class IntroductionSlide(
    @RawRes val animationResId: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
)

internal val introductionSlides = listOf(
    IntroductionSlide(
        animationResId = CommonR.raw.first_slide,
        title = CommonR.string.slide_title_explore_books,
        description = CommonR.string.slide_description_explore_books
    ),
    IntroductionSlide(
        animationResId = CommonR.raw.second_slide,
        title = CommonR.string.slide_title_read_anytime,
        description = CommonR.string.slide_description_read_anytime
    ),
    IntroductionSlide(
        animationResId = CommonR.raw.third_slide,
        title = CommonR.string.slide_title_build_library,
        description = CommonR.string.slide_description_build_library
    )
)
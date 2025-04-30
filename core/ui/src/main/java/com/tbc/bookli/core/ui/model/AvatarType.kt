package com.tbc.bookli.core.ui.model

import com.tbc.bookli.core.common.R

enum class AvatarType(val key: String, val drawableRes: Int) {
    ALIEN("alien", R.drawable.ic_alien),
    COOL("cool", R.drawable.ic_cool),
    GIRAFFE("giraffe", R.drawable.ic_giraffe),
    GORILLA("gorilla", R.drawable.ic_gorilla),
    HACKER("hacker", R.drawable.ic_hacker),
    MAN("man", R.drawable.ic_man),
    MAN_GRAY("man_gray", R.drawable.ic_man_gray),
    RABBIT("rabbit", R.drawable.ic_rabbit),
    SPACESHIP("spaceship", R.drawable.ic_spaceship),
    TIGER("tiger", R.drawable.ic_tiger),
    WOMAN_RED("woman_red", R.drawable.ic_woman_red),
    WOMAN_YELLOW("woman_yellow", R.drawable.ic_woman_yellow);

    companion object {
        fun fromKey(key: String): AvatarType {
            return entries.firstOrNull { it.key.equals(key, ignoreCase = true) } ?: RABBIT
        }
    }
}

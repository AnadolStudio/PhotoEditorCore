package com.anadolstudio.photoeditorcore.functions

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.anadolstudio.photoeditorcore.R

interface FuncItem {
    val textId: Int
    val drawableId: Int

    enum class InnerFunctionItem(
            @StringRes override val textId: Int,
            @DrawableRes override val drawableId: Int,
    ) : FuncItem {

        //Тут описанны все внутренние функции
        CROP(R.string.edit_func_crop, R.drawable.ic_crop),
        TURN(R.string.crop_func_turn, R.drawable.ic_rotate),
        FLIP_HORIZONTAL(R.string.crop_func_flip_h, R.drawable.ic_flip_horizontal),
        FLIP_VERTICAL(R.string.crop_func_flip_v, R.drawable.ic_flip_vertical),
    }

    enum class MainFunctions(
            @StringRes override val textId: Int,
            @DrawableRes override val drawableId: Int,
            val innerFunctions: List<InnerFunctionItem> = listOf()
    ) : FuncItem {

        TRANSFORM(
                R.string.crop_func_transform,
                R.drawable.ic_transform,
                listOf(
                        InnerFunctionItem.CROP,
//                InnerFunctionItem.TURN,
                        InnerFunctionItem.FLIP_HORIZONTAL,
                        InnerFunctionItem.FLIP_VERTICAL
                )
        ),

        //        FILTER(R.string.edit_func_filter, R.drawable.ic_filter),
        EFFECT(R.string.edit_func_effect, R.drawable.ic_effect),
        CUT(R.string.edit_func_cut, R.drawable.ic_cut),

        /*BLUR(R.string.edit_func_blur, R.drawable.ic_blur),
        EXPOSITION(R.string.edit_func_exposition, R.drawable.ic_overlap),
        FIT(R.string.edit_func_fit, R.drawable.ic_fit),
        MIRROR(R.string.edit_func_mirror, R.drawable.ic_mirror),
        ADD_IMAGE(R.string.edit_func_add, R.drawable.ic_add_image),
        TEXT(R.string.edit_func_text, R.drawable.ic_text),*/

        STICKER(R.string.edit_func_sticker, R.drawable.ic_sticker),
        BRUSH(R.string.edit_func_brush, R.drawable.ic_brush);
    }
}

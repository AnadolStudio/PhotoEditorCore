package com.anadolstudio.photoeditorcore.functions.effect

import com.anadolstudio.photoeditorcore.functions.EditFunction
import com.anadolstudio.photoeditorcore.functions.FuncItem

class EffectFunction : EditFunction.Abstract(FuncItem.MainFunctions.EFFECT) {
    private lateinit var pathEffect: String

    fun setPath(pathEffect: String) {
        this.pathEffect = pathEffect
    }

    override fun reboot() {
        pathEffect = ""
    }
}

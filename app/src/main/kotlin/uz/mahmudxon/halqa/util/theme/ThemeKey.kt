package uz.mahmudxon.halqa.util.theme

import dagger.MapKey
import kotlin.reflect.KClass


@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.CLASS)
@MapKey
annotation class ThemeKey(val value: KClass<out Theme>)
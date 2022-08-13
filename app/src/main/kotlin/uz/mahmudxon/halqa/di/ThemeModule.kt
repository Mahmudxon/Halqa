package uz.mahmudxon.halqa.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import uz.mahmudxon.halqa.util.theme.Theme
import uz.mahmudxon.halqa.util.theme.themes.ClassicTheme
import uz.mahmudxon.halqa.util.theme.themes.NightTheme

@InstallIn(SingletonComponent::class)
@Module
object ThemeModule {

    @Provides
    @IntoSet
    fun provideClassicTheme(classic: ClassicTheme): Theme = classic

    @Provides
    @IntoSet
    fun provideNightTheme(nightTheme: NightTheme): Theme = nightTheme
}

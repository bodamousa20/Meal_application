    package com.example.meals_app.Database

    import android.content.Context
    import androidx.room.Database
    import androidx.room.Room
    import androidx.room.RoomDatabase
    import com.example.meals_app.Data.Meal

    @Database(entities = [MealDATA::class], version = 1, exportSchema = false)
    abstract class MealsDatabase :RoomDatabase(){
        abstract fun mealDao(): MealsDAO
        companion object {
            @Volatile
            private var INSTANCE: MealsDatabase? = null

            fun getDatabase(context: Context): MealsDatabase {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MealsDatabase::class.java,
                        "meal_database"
                    ).build()
                    INSTANCE = instance
                    instance
                }
            }
        }


    }
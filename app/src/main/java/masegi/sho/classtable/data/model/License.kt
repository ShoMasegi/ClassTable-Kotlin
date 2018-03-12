package masegi.sho.classtable.kotlin.data.model

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by masegi on 2018/03/04.
 */

enum class License(val filePath: String) {

    APACHE("Apache.txt"),
    MIT("MIT.txt");

    fun getContent(context: Context): String {

        var text = ""
        var reader: BufferedReader? = null

        try {

            reader = BufferedReader(
                    InputStreamReader(context.assets.open("licenses/" + filePath))
            )
            while (true) {

                val line = reader.readLine() ?: break
                text += (line + "\n")
            }
        }
        catch (e: IOException) {

            e.printStackTrace()
        }
        finally {

            reader?.let { it.close() }
        }
        return text
    }
}

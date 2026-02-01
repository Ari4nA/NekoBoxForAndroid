package io.nekohasekai.sagernet.bg.proto

import io.nekohasekai.sagernet.database.DataStore
import io.nekohasekai.sagernet.database.ProxyEntity
import kotlinx.coroutines.delay

class UrlTest {

    val link = DataStore.connectionTestURL
    private val timeout = 5000

    suspend fun doTest(profile: ProxyEntity): Int {
        var totalPing: Long = 0 // مجموع پینگ‌ها
        var successCount = 0    // تعداد تست‌های موفق
        val testCount = 10      // تعداد کل تست‌ها

        repeat(testCount) {
            val result = TestInstance(profile, link, timeout).doTest()

            if (result > 0) {
                totalPing += result // جمع کردن پینگ فعلی با کل
                successCount++      // اضافه کردن به شمارنده موفق‌ها
            }
            
            // تاخیر کوتاه بین تست‌ها
            if (it < testCount - 1) delay(20)
        }

        // محاسبه میانگین: مجموع تقسیم بر تعداد
        return if (successCount > 0) {
            (totalPing / successCount).toInt()
        } else {
            -1 // اگر هیچ تستی موفق نبود
        }
    }
}

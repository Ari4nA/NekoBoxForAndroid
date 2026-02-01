package io.nekohasekai.sagernet.bg.proto

import io.nekohasekai.sagernet.database.DataStore
import io.nekohasekai.sagernet.database.ProxyEntity
import kotlinx.coroutines.delay

class UrlTest {

    val link = DataStore.connectionTestURL
    private val timeout = 5000

    suspend fun doTest(profile: ProxyEntity): Int {
        var bestPing = Int.MAX_VALUE // فرض اولیه: بدترین پینگ ممکن
        var isSuccess = false
        val testCount = 6 // تعداد دفعات تست (درخواست شما)

        repeat(testCount) {
            // انجام تست تکی
            val result = TestInstance(profile, link, timeout).doTest()

            // اگر نتیجه معتبر بود (بزرگتر از ۰ معمولا یعنی موفقیت)
            if (result > 0) {
                isSuccess = true
                if (result < bestPing) {
                    bestPing = result // اگر پینگ جدید کمتر بود، آن را ذخیره کن
                }
            }

            // (اختیاری) تاخیر کوتاه ۵۰ میلی‌ثانیه‌ای بین تست‌ها برای دقت بیشتر
            // اگر نمی‌خواهید می‌توانید خط زیر را حذف کنید
            delay(50)
        }

        // اگر حداقل یک تست موفق بود، بهترین پینگ را برگردان، وگرنه -1 (یا 0 بسته به منطق برنامه)
        return if (isSuccess) bestPing else -1
    }

}

package fake

import java.io.ByteArrayOutputStream
import java.io.InputStream

object Utils {


    fun getFakeResponseInterceptByHttpStatusCode(httpStatusCode : Int, message : String ) : FakeApiInterceptor{
        val fakeApiInterceptor = FakeApiInterceptor()
        val fakeApiResponse = FakeApiResponse()
        fakeApiResponse.status = httpStatusCode
        fakeApiResponse.responseString = message
        fakeApiInterceptor.setFakeApiResponse(fakeApiResponse)
        return fakeApiInterceptor
    }

    fun getFakeResponseInterceptFromFile(fileName : String) : FakeApiInterceptor{
        val fakeApiInterceptor = FakeApiInterceptor()
        val fakeApiResponse = FakeApiResponse()
        fakeApiResponse.status = 200
        fakeApiResponse.responseString = readResFile(fileName)
        fakeApiInterceptor.setFakeApiResponse(fakeApiResponse)
        return fakeApiInterceptor
    }

    fun readResFile(resourceName: String) = readInputStreamToString(
        javaClass.classLoader!!.getResourceAsStream(resourceName)
    )

    private fun readInputStreamToString(inputStream: InputStream): String {
        val result = ByteArrayOutputStream()
        var read: Int = -1
        inputStream.use { input ->
            result.use {
                while (input.read().also { read = it } != -1) {
                    it.write(read)
                }
            }
        }
        return result.toString("UTF-8")
    }
}

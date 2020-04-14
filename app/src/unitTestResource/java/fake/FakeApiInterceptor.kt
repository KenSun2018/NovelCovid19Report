package fake

import okhttp3.*


class FakeApiInterceptor  : Interceptor {
    companion object{
        const val TAG = "fake.FakeApiInterceptor";
    }

    private var fakeResponse : FakeApiResponse ? = null


    fun setFakeApiResponse(fakeApiResponse: FakeApiResponse){
        this.fakeResponse = fakeApiResponse
    }

    fun getFakeResponseString() : String?{
        return this.fakeResponse?.responseString
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        if(fakeResponse == null){
            val original = chain.request()

            val request = original.newBuilder()
                .method(original.method(), original.body())
                .build()

            return chain.proceed(request)
        }

        val fakeApiResponse = fakeResponse!!


        return chain.proceed(chain.request())
            .newBuilder()
            .code(fakeApiResponse.status)
            .protocol(Protocol.HTTP_2)
            .message(fakeApiResponse.responseString)
            .body(
                ResponseBody.create(
                    MediaType.parse("application/json"),
                    fakeApiResponse.responseString.toByteArray()
                )
            )
            .addHeader("content-type", "application/json")
            .build()

    }
}

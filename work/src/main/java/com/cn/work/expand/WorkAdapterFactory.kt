//package com.cn.http.work.expand
//
//import com.cn.http.work.base.WorkObservable
//import retrofit2.*
//import java.lang.reflect.ParameterizedType
//import java.lang.reflect.Type
//
//class WorkAdapterFactory : CallAdapter.Factory() {
//    companion object {
//        fun create() = WorkAdapterFactory()
//    }
//
//    override fun get(
//        returnType: Type,
//        annotations: Array<Annotation>,
//        retrofit: Retrofit
//    ): CallAdapter<*, *>? {
//        val rawObservableType = getRawType(returnType)
//        if (rawObservableType == WorkObservable::class.java && returnType is ParameterizedType) {
//            val observableType = getParameterUpperBound(0, returnType)
//            return RequestAdapter<Any>(
//                observableType
//            )
//        }
//        return null
//    }
//
//    class RequestAdapter<T>(val type: Type) : CallAdapter<T, WorkObservable<T>> {
//        override fun adapt(call: Call<T>): WorkObservable<T> {
//            return doWork {
//                call.enqueue(object : Callback<T> {
//                    override fun onFailure(call: Call<T>, t: Throwable) {
//                        it.onError(t)
//                    }
//
//                    override fun onResponse(call: Call<T>, response: Response<T>) {
//                        if (response.isSuccessful) {
//                            val body = response.body()
//                            body?.apply { it.onNext(this) }
//                        } else {
//                            it.onError(Exception(response.errorBody()?.string() ?: "failure"))
//                        }
//                    }
//                })
//            }
//        }
//
//        override fun responseType(): Type {
//            return type
//        }
//
//
//    }
//}
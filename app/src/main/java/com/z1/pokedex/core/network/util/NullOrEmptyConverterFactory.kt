package com.z1.pokedex.core.network.util

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * O método "responseBodyConverter" é sobrescrito nesta classe para criar um conversor personalizado para converter o corpo da resposta da API em um objeto.
 * Esse conversor primeiro chama o conversor padrão do Retrofit, que é retornado pelo método "nextResponseBodyConverter",
 * e depois verifica se o comprimento do conteúdo da resposta é zero.
 * Se for zero, significa que a resposta está vazia, e o conversor personalizado retorna "null".
 * Caso contrário, o conversor padrão do Retrofit é usado para converter o corpo da resposta em um objeto.
 *
 * Essa implementação é útil quando queremos tratar uma resposta vazia como nula, para evitar exceções ou
 * comportamentos indesejados em casos de retorno de API sem conteúdo.
 *
 * Tratando a Exception End of input at line 1 column 1 path $
 */
class NullOrEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val delegate: Converter<ResponseBody, *> =
            retrofit.nextResponseBodyConverter<Any>(this, type, annotations)

        return Converter { body ->
            if (body.contentLength() == 0L) null else delegate.convert(
                body
            )
        }
    }
}
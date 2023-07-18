package com.woosung.data.mapper

import com.woosung.data.model.DataModel
import com.woosung.domain.DomainModel


/**
 * MAPPER에 제한을 걸기 위해 사용했다.
 * 타입 제한
 * @param Data
 * @param Domain
 */
internal fun interface Mapper<Data : DataModel, Domain : DomainModel> {
    fun mapDomain(input: Data): Domain
}

internal fun interface ListMapper<Data : DataModel, Domain : DomainModel> {
    fun mapDomainList(input: List<Data>): List<Domain>
}

internal inline fun <Data : DataModel, Domain : DomainModel> List<Data>.mapDomainList(
    mapSingle: (Data) -> Domain,
): List<Domain> {
    return map { mapSingle(it) }
}

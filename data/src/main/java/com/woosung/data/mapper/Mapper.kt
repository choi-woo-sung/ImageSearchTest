package com.woosung.data.mapper

import com.woosung.data.model.DataModel
import com.woosung.domain.DomainModel

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

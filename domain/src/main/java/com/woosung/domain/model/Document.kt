package com.woosung.domain.model

import com.woosung.domain.DomainModel


/**
 * 키와 같이 들어간 클래스
 *
 * @property key
 * @property document
 * @constructor Create empty Document with key
 */

data class DocumentWithKey(
    val key: Int,
    val document: Document,
) : DomainModel


/**
 * Document Data (Data 공통 Interface)
 *
 * @property datetime : 문서 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
 */
sealed class Document() : DomainModel {
    abstract val dateTime: DateTime
    abstract val isBookMarked: Boolean
}
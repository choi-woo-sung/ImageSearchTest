package com.woosung.domain.model

import com.woosung.domain.DomainModel

/**
 * Document Data (Data 공통 Interface)
 *
 * @property datetime : 문서 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
 */
sealed class Document(open val dateTime: DateTime) : DomainModel
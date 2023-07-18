package com.woosung.data.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.woosung.domain.model.Document
import com.woosung.domain.model.Image
import com.woosung.domain.model.Video
import javax.inject.Inject

interface SearchSharedPreference {
    fun addDocument(document: Document): Unit
    fun updateDocuments(documents: MutableList<Pair<String, String>>): Unit
    fun removeDocument(document: Document): Unit
    fun getDocumentPairs(): MutableList<Pair<String, String>>
    fun getDocuments(): LinkedHashSet<Document>
}


class SearchSharedPreferenceImpl @Inject constructor(
    private val pref: SharedPreferences,
) : SearchSharedPreference {

    private val editor by lazy { pref.edit() }
    private val gson by lazy { Gson() }



    //콜백flow로 래핑하여 해결했어도 되는 부분이 아니였을까?

    /**
     * 단일 Document 저장
     *
     * @param document : ImageDocument 또는 VClipDocument data
     */
    override fun addDocument(document: Document) {
        val key = when (document) {
            is Image -> IMAGE_DOCUMENTS
            is Video -> VIDEO_DOCUMENTS
        }

        val documents = getDocumentPairs()
        documents.add(Pair(key, gson.toJson(document)))
        editor.putString(PREF_DOCUMENTS, gson.toJson(documents))
        editor.apply()

    }

    /**
     * 전체 Document 갱신
     *
     * @param documents : 갱신할 전체 Document
     */
    override fun updateDocuments(documents: MutableList<Pair<String, String>>) {
        editor.putString(PREF_DOCUMENTS, gson.toJson(documents))
        editor.apply()
    }

    /**
     * 단일 Document 삭제
     *
     * @param document : ImageDocument 또는 VClipDocument data
     */
    override fun removeDocument(document: Document) {
        var target: Pair<String, String>? = null
        val jsonDocument = gson.toJson(document)
        val documentPairs = getDocumentPairs()
        for (pair in documentPairs) {
            if (pair.second == jsonDocument) {
                target = pair
                break
            }
        }
        target?.let {
            documentPairs.remove(target)
            updateDocuments(documentPairs)
        }
    }

    /**
     * Document pair 리스트 요청
     *
     * @return Document pairs : <first:구분값, second:데이터>
     */
    override fun getDocumentPairs(): MutableList<Pair<String, String>> {
        val data = pref.getString(PREF_DOCUMENTS, null)
        var pairs = mutableListOf<Pair<String, String>>()
        data?.let {
            val itemType = object : TypeToken<MutableList<Pair<String, String>>>() {}.type
            pairs = gson.fromJson(data, itemType)
        }
        return pairs
    }

    /**
     * Document 전체 요청
     *
     * @return Document 전체 리스트
     */
    override fun getDocuments(): LinkedHashSet<Document> {
        val documents = linkedSetOf<Document>()
        for (pair in getDocumentPairs()) {
            if (pair.first == IMAGE_DOCUMENTS) {
                documents.add(gson.fromJson(pair.second, Image::class.java))
            } else {
                documents.add(gson.fromJson(pair.second, Video::class.java))
            }
        }
        return documents
    }

    companion object {
        const val PREF_DOCUMENTS = "PREF_DOCUMENTS" // Document 전체 저장 Key
        const val IMAGE_DOCUMENTS = "IMAGE_DOCUMENT" // ImageDocument 구분용 Pair Key
        const val VIDEO_DOCUMENTS = "VIDEO_DOCUMENTS" // VClipDocument 구분용 Pair Key
    }
}
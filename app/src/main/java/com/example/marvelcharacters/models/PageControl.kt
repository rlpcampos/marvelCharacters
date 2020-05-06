package com.example.marvelcharacters.models

class PageControl(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val total: Int
) {
    constructor(dataContainer: CharacterDataContainer) : this(
        count = dataContainer.count,
        limit = dataContainer.limit,
        offset = dataContainer.offset,
        total = dataContainer.total
    )

    val page: Int
        get() = (offset / limit) + 1
    val pageEnd: Int
        get() = (total / limit) + if (count == limit) 0 else 1
    val hasNextPage: Boolean
        get() = page < pageEnd
}

package net.nextglobe.expresscloud.api.server

import net.nextglobe.expresscloud.api.category.Category

interface CategorizedServer : Server {

    val category: Category
    override val name: String
        get() = category.name + "-" + id.toString().substring(0, 7)

}
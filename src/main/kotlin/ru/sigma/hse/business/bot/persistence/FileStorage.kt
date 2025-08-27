package ru.sigma.hse.business.bot.persistence

interface FileStorage {
    /**
     * Сохраняет файл в хранилище. Если файл с таким именем уже существует, то он перезаписывается.
     * @param path путь к файлу, включающий имя файла
     * @param file содержимое файла
     */
    fun save(path: String, file: ByteArray)
}
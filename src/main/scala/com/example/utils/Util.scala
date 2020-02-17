package com.example.utils

import com.example.utils.DTO.Entity

import scala.io.Source

object Util {

  private val fileContentSplitter: String = ","

  /**
   * reads the source file with a predefined schema, skipping the first line
   */
  def readResourceFile(path: String): List[Entity] = {
    val res = Source.fromResource(path)
    val fileContent = res.getLines.toList.tail
      .map(companyRecord => Entity(companyRecord.split(fileContentSplitter).head, companyRecord.split(fileContentSplitter).last))
    res.close()
    fileContent
  }
}

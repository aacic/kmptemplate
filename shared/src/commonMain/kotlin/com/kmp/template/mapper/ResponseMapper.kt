package com.kmp.template.mapper

import com.kmp.template.domain.MyDomainObject
import com.kmp.template.dto.MyDto

object ResponseMapper {

   fun map(responseDto: MyDto): MyDomainObject {
        return MyDomainObject(responseDto.myText)
    }
}
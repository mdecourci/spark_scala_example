package com.netpod.retail.service

import com.netpod.application.domain.{Region, Shop, Till, Sale}


/**
 * Created by michaeldecourci on 11/08/15.
 */
trait Retail {
    def log(sale: Sale)
    def findSales(till: Till) : List[Sale]
    def findSales(shop: Shop) : List[Sale]
    def findSales(forRegion: Region) : List[Sale]
}

package com.cbmie.webservice.goods;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.cbmie.genMac.shop.entity.Goods;
import com.cbmie.genMac.shop.entity.GoodsType;
import com.cbmie.genMac.shop.service.GoodsService;
import com.cbmie.genMac.shop.service.GoodsTypeService;

@WebService
public class MobileGoodsDetail {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsTypeService goodsTypeService;
	
	@WebMethod
	public GoodsObject findGoodsDetail(String bussinessKey){
		GoodsObject goodsObject = new GoodsObject();
		Long id = Long.parseLong(bussinessKey);
		
		Goods goods = goodsService.get(id);
		GoodsType goodsType = goodsTypeService.get(goods.getGoodsTypeId());
		
		goodsObject.setBrief(goods.getBrief());
		goodsObject.setCover(goods.getCover());
		goodsObject.setGoodsTypeName(goodsType.getName());
		goodsObject.setId(goods.getId().toString());
		goodsObject.setImg(goods.getImg());
		goodsObject.setIntroduce(goods.getIntroduce());
		goodsObject.setIsSold(goods.getIsSold());
		goodsObject.setMarketPrice(goods.getMarketPrice()==null?"":goods.getMarketPrice().toString());
		goodsObject.setName(goods.getName());
		goodsObject.setPostage(goods.getPostage()==null?"":goods.getPostage().toString());
		goodsObject.setPrice(goods.getPrice()==null?"":goods.getPrice().toString());
		goodsObject.setSales(goods.getSales()==null?"":goods.getSales().toString());
		
		return goodsObject;
	}

}

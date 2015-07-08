package com.nortal.spring.cw.jsp.web.tag.list;

/**
 * Enumit kasutatakse tagis listCellFragment. Enumi alusel määratakse mis
 * positsioonil antud lahtrit näidatakse, kas tabeli päises, tabeli kehas või
 * jaluses. Koodi kasutatakse listi tagis objektide päringuskoobis
 * identifitseerimiseks
 * 
 * @author Margus Hanni
 * @since 19.03.2013
 */
public enum ListCellFragmentPosition {

	THEAD("H"), TBODY("B"), TFOOT("F");

	private String code;

	private ListCellFragmentPosition(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}

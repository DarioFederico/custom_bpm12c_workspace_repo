
	Array.prototype.unique = function() {
		var a = {};
		for(var i=0; i <this.length; i++){
			if(typeof a[this[i]] == "undefined")
				a[this[i]] = 1;
			}
		this.length = 0;
		for(var i in a)
			this[this.length] = i;
		return this;
    }
	
	loadTaskForm = function() {
		
		var xmlUtil;
		var fielddata="";
		var arrayfielddata="";
		var payloadDom = xmlDOM($("#xpayload").val());

		xmlUtil = new XMLUtil(payloadDom[0]);

		var processName = xmlUtil.selectSingleNode("//payload:PROCESS_DOC/payload:PROCESS_NAME/text()");
		var businessDataDoc = xmlUtil.selectSingleNode("//payload:PROCESS_DOC/payload:BUSINESS_DATA_DOC");
		var datasDoc = xmlUtil.selectNodes("//payload:PROCESS_DOC/payload:BUSINESS_DATA_DOC/DATAS");
		var bodyDoc = xmlUtil.selectSingleNode("//payload:PROCESS_DOC/payload:BUSINESS_DATA_DOC/DATAS[0]/BODY");
		
		var dataNode = xmlUtil.selectSingleNode("//payload:PROCESS_DOC/payload:BUSINESS_DATA_DOC/payload:DATAS[1]/payload:BODY/payload:DATA");
		var data_cnt = xmlUtil.nodeCount("//payload:PROCESS_DOC/payload:BUSINESS_DATA_DOC/payload:DATAS[1]/payload:BODY/payload:DATA");
		var bizDataNode;
		
		if(data_cnt > 0) {
			bizDataNode = dataNode.childNodes;
		} else {
			alert("No Data....... plz contact admin..");
		}
		
		var array_data_cnt = xmlUtil.nodeCount("//payload:PROCESS_DOC/payload:BUSINESS_DATA_DOC/payload:DATAS[1]/payload:BODY/payload:ARRAY_DATA");
		var arrayDataNode;
		var bizCollectionDataNode;
		
		//array data 존재...
		if(array_data_cnt > 0) {
			arrayDataNode = xmlUtil.selectSingleNode("//payload:PROCESS_DOC/payload:BUSINESS_DATA_DOC/payload:DATAS[1]/payload:BODY/payload:ARRAY_DATA");
			bizCollectionDataNode = arrayDataNode.childNodes;
		}

		for(var i=0; i<bizDataNode.length; i++) {								
			if(bizDataNode[i].nodeType == Node.ELEMENT_NODE) {
				
				if(bizDataNode[i].hasChildNodes()) {
					for(var j=0; j<bizDataNode[i].childNodes.length; j++) {		
						if(bizDataNode[i].childNodes[j].nodeType == Node.ELEMENT_NODE) {
							
							fielddata += "<fieldset name=\""+bizDataNode[i].childNodes[j].nodeName.replace(prefix+":", "")+"\" type=\"single\">\n";
							fielddata += "<h3>"+schemainfo['kospo.schema.displayname.'+bizDataNode[i].childNodes[j].nodeName.replace(prefix+":", "")]+"</h3>\n";
							
							if(bizDataNode[i].childNodes[j].hasChildNodes()) {
								for(var k=0; k<bizDataNode[i].childNodes[j].childNodes.length; k++) {
									if(bizDataNode[i].childNodes[j].childNodes[k].nodeType == Node.ELEMENT_NODE) {
										// Custom Schema 의 두번째 하위노드 (실제 화면상에 보여줄 데이터)
										// 첫번째 하위노드.현재 하위노드 형태로 각 요소별 속성을 별도의 Property로 저장하여 가져와 UI를 구성한다.....
										//alert(bizDataNode[i].childNodes[j].childNodes[k].nodeName + ":" + bizDataNode[i].childNodes[j].childNodes[k].textContent);	
										
										//기본 타입은 input text
										if(schemainfo['kospo.schema.display.'+bizDataNode[i].childNodes[j].childNodes[k].nodeName.replace(prefix+":", "")] == 'hidden') {
											fielddata += "		<input class=\"input-xlarge focused\" id=\""+bizDataNode[i].childNodes[j].childNodes[k].nodeName.replace(prefix+":", "")+"\" name=\""+bizDataNode[i].childNodes[j].childNodes[k].nodeName.replace(prefix+":", "")+"\" type=\""+schemainfo['kospo.schema.display.'+bizDataNode[i].childNodes[j].childNodes[k].nodeName.replace(prefix+":", "")]+"\" value=\""+bizDataNode[i].childNodes[j].childNodes[k].textContent+"\">\n";
										} else {
											fielddata += "<div class=\"control-group\">\n";
											fielddata += "	<label class=\"control-label\" for=\"focusedInput\">"+schemainfo['kospo.schema.displayname.'+bizDataNode[i].childNodes[j].childNodes[k].nodeName.replace(prefix+":", "")]+"</label>\n";
											fielddata += "	<div class=\"controls\">\n";
											fielddata += "		<input class=\"input-xlarge focused\" id=\""+bizDataNode[i].childNodes[j].childNodes[k].nodeName.replace(prefix+":", "")+"\" name=\""+bizDataNode[i].childNodes[j].childNodes[k].nodeName.replace(prefix+":", "")+"\" type=\"text\" "+schemainfo['kospo.schema.readonly.'+bizDataNode[i].childNodes[j].childNodes[k].nodeName.replace(prefix+":", "")]+" value=\""+bizDataNode[i].childNodes[j].childNodes[k].textContent+"\">\n";
											fielddata += "	</div>\n";
											fielddata += "</div>\n";
										}
									}
								}
							}
							fielddata += "</fieldset>\n";
						}
					}
				}
			}
		}

		//$('#form_data')[0].innerHTML = html;
		
		//	Array Data
		var main_table_title = new Array();
		var sub_table_title = new Array();
		var row_cnt = 0;
		var col_cnt = 0;
		
		if(bizCollectionDataNode != null) {
			for(var i=0; i<bizCollectionDataNode.length; i++) {								
				if(bizCollectionDataNode[i].nodeType == Node.ELEMENT_NODE) {
					//alert(bizCollectionDataNode[i].nodeName);							// Import Schema의 첫번째 마스터 노드 : e.g MaterialArrayInfo
					if(bizCollectionDataNode[i].hasChildNodes()) {
						
						var arrayLv1 = bizCollectionDataNode[i].childNodes;
										
						for(var j=0; j<arrayLv1.length; j++) {
							if(arrayLv1[j].nodeType == Node.ELEMENT_NODE) {
								//alert(arrayLv1[j].nodeName);							// Import Schema의 두번째 마스터 노드 : e.g SupplierResults
								var arrayLv2 = arrayLv1[j].childNodes;
								
								arrayfielddata += "<fieldset name=\""+arrayLv1[j].nodeName.replace(prefix+":", "")+"\" type=\"array\">\n";
								arrayfielddata += "<div class=\"row-fluid\">\n";
								arrayfielddata += "<div class=\"span12\">\n";
								arrayfielddata += "<div class=\"box-content\">\n";
								arrayfielddata += "<table class=\"table table-bordered table-striped table-condensed\">\n";
								
								var row_data = new Array();
								var col_data = new Array();
								for(var k=0; k<arrayLv2.length; k++) {
									if(arrayLv2[k].nodeType == Node.ELEMENT_NODE) {
										
										row_cnt++;
										//alert(arrayLv2[k].nodeName);				// Import Schema의 세번째 마스터 노드 : e.g SupplierResult <tr>
										var arrayLv3 = arrayLv2[k].childNodes;
										
										col_data[row_cnt] = new Array();
										col_cnt=0;
										for(var l=0; l<arrayLv3.length; l++) {
											if(arrayLv3[l].nodeType == Node.ELEMENT_NODE) {
												
												//alert(arrayLv3[l].nodeName);				
												main_table_title.push(arrayLv3[l].nodeName);	// Import Schema의 네번재 마스터 노드 : e.g Supplier, SupplierDetermine 
												var arrayLv4 = arrayLv3[l].childNodes;
												
												for(var m=0; m<arrayLv4.length; m++) {
													if(arrayLv4[m].nodeType == Node.ELEMENT_NODE) {
														col_cnt++;
														col_data[row_cnt][col_cnt] = new Array();
														//alert(arrayLv3[l].nodeName+"["+row_cnt+"]" + "=>" + arrayLv4[m].nodeName + ":" + arrayLv4[m].textContent);				// Import Schema의 다섯번째 마스터 노드
														sub_table_title.push(arrayLv4[m].nodeName);
														col_data[row_cnt][col_cnt] = arrayLv4[m].textContent ;
														
													}
												}
											}
										}
										//alert(row_cnt + ":"+col_data.length+ " 담음....");
										row_data.push(col_data);
									}
								}
								
								main_table_title = main_table_title.unique();
								sub_table_title = sub_table_title.unique();
								row_data = row_data;

								arrayfielddata += "<thead>\n";
								arrayfielddata += "<tr>\n";
								for(var mt_idx=0; mt_idx<main_table_title.length; mt_idx++) {
									arrayfielddata += "<th colspan=\"4\" style=\"text-align:center\">"+schemainfo['kospo.schema.displayname.'+main_table_title[mt_idx].replace(prefix+":", "")]+"</th>\n";
								}
								arrayfielddata += "</tr>\n";
								arrayfielddata += "<tr>\n";
								for(var st_idx=0; st_idx<sub_table_title.length; st_idx++) {
									if(schemainfo['kospo.schema.display.'+sub_table_title[st_idx].replace(prefix+":", "")] != 'hidden') {
										arrayfielddata += "<th>"+schemainfo['kospo.schema.displayname.'+sub_table_title[st_idx].replace(prefix+":", "")]+"</th>\n";
									}
								}
								arrayfielddata += "</tr>\n";
								arrayfielddata += "</thead>\n";
								
								arrayfielddata += "<tbody>\n";
								
								for(var row_idx=0; row_idx<row_data.length; row_idx++) {
									arrayfielddata += "<tr style=\"cursor:pointer\" onclick=\"setRowData(this)\">\n";
									for(var col_idx=0; col_idx<col_data[row_idx+1].length-1; col_idx++) {
										//alert(row_idx +":"+col_idx);
										//alert(col_data[row_idx+1][col_idx+1]);
										
										
										if(schemainfo['kospo.schema.display.'+sub_table_title[col_idx].replace(prefix+":", "")] != 'hidden') {
											arrayfielddata += "<td name=\""+sub_table_title[col_idx].replace(prefix+":", "")+"\">"+col_data[row_idx+1][col_idx+1]+"</td>\n";
										} else {
											arrayfielddata += "<td style=\"display:none\" name=\""+sub_table_title[col_idx].replace(prefix+":", "")+"\">"+col_data[row_idx+1][col_idx+1]+"</td>\n";
										}
									}
									arrayfielddata += "<tr>\n";
								}
								
								arrayfielddata += "<tbody>\n";
								arrayfielddata += "</table>\n";
								arrayfielddata += "</div>\n";
								arrayfielddata += "</div>\n";
								arrayfielddata += "</div>\n";
								arrayfielddata += "</fieldset>\n";
							}
						}
					}
				}
			}
		}

		$("#form_data").html(fielddata +" \n" + arrayfielddata);

	}
	
	setRowData = function(obj) {
		if(confirm("공급사를 변경하시겠습니까?")) {
			$('td', obj).each (function() {
				$("#"+$(this).attr("name")+"").val($(this).text());
			});
		} else return;
	}
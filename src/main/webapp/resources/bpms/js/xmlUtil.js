/*
 * author : 김동후 (dhkim@built1.com)
 * version : 1.0
 * description : XML Utility
 * 
 * */
// Explorer 외 브라우저에서 xpath 사용시 기본 네임스페이스 를 사용하여 노드 접근시 필요함.
var replaceDefaultPrefix = "payload";	

(function($) {
	/*
	 * XMLUtil 생성자
	 * param 1 :
	 *     - xmlDom (Dom Object) : 파싱할 대상 XML Dom Object
	 * Description : XMLUtil 생성자
	 * */
	XMLUtil = function(xmlDom) {
		if(xmlDom == undefined) {
			throw Error("[XMLUtil.Initiator] required Dom Object");
		}
		this.xmlDom = xmlDom;
	};
	// IE DOMParser wrapper
	/*if ( window['DOMParser'] == undefined && window.ActiveXObject ) {
		DOMParser = function() { };
		DOMParser.prototype.parseFromString = function( xmlString ) {
			var doc = new ActiveXObject('Microsoft.XMLDOM');
	        doc.async = 'false';
	        doc.loadXML( xmlString );
	        
			return doc;
		};
	}
	xmlDOM2 = function(xml, onErrorFn) {
		try {
			var xmlDoc 	= ( new DOMParser() ).parseFromString( xml, 'text/xml' );
			if ( $.isXMLDoc( xmlDoc ) ) {
				var err = $('parsererror', xmlDoc);
				if ( err.length == 1 ) {
					throw('Error: ' + $(xmlDoc).text() );
				}
			} else {
				throw('Unable to parse XML');
			}
		} catch( e ) {
			var msg = ( e.name == undefined ? e : e.name + ': ' + e.message );
			if ( $.isFunction( onErrorFn ) ) {
				onErrorFn( msg );
			} else {
				$(document).trigger('xmlParseError', [ msg ]);
			}
			return $([]);
		}
		return $( xmlDoc );
	};*/
	// xml 문서를 $(Dom) 객체로 반환
	xmlDOM = function(xml, onErrorFn) {
		try {
			
			var xmlDoc 	= $.parseXML(xml);

			//pi = xmlDoc.createProcessingInstruction("xml", "version=\"1.0\" encoding=\"utf-8\"");
			//xmlDoc.insertBefore(pi, xmlDoc.documentElement)

			if ( $.isXMLDoc( xmlDoc ) ) {
				var err = $('parsererror', xmlDoc);
				if ( err.length == 1 ) {
					throw('Error: ' + $(xmlDoc).text() );
				}
			} else {
				throw('Unable to parse XML');
			}
		} catch( e ) {
			var msg = ( e.name == undefined ? e : e.name + ': ' + e.message );
			if ( $.isFunction( onErrorFn ) ) {
				onErrorFn( msg );
			} else {
				$(document).trigger('xmlParseError', [ msg ]);
			}
			return $([]);
		}
		return $( xmlDoc );
	};
	
	// xml 문서를 포멧된 형태로 반환
	formatXml = function (xml) {
		var formatted = '';
		var reg = /(>)(<)(\/*)/g;
		xml = xml.replace(reg, '$1\r\n$2$3');
		
		var pad = 0;
		
		jQuery.each(xml.split('\r\n'), function(index, node) {
			var indent = 0;
			
			if (node.match( /.+<\/\w[^>]*>$/ )) {
				indent = 0;
			} else if (node.match( /^<\/\w/ )) {
				if (pad != 0) {
					pad -= 1;
				}
			} else if (node.match( /^<\w[^>]*[^\/]>.*$/ )) {             
				indent = 1;         
			} else {             
				indent = 0;         
			}
			
			var padding = '';         
			for (var i = 0; i < pad; i++) {
				padding += '  ';
			}
			
			formatted += padding + node + '\r\n';
			pad += indent;
		});
		
		return formatted;
	};
	
	// Dom 객체를 XML String 형태로 반환
	xmlStr = function (xmlObject, onErrorFn) {
		var xmlString;
		try {
			//IE
			if (window.ActiveXObject) {
				xmlString = xmlObject.xml;
			} else {
				xmlString = (new XMLSerializer()).serializeToString(xmlObject);
			}
			
		} catch( e ) {
			var msg = ( e.name == undefined ? e : e.name + ': ' + e.message );
			if ( $.isFunction( onErrorFn ) ) {
				onErrorFn( msg );
			} else {
				$(document).trigger('xmlParseError', [ msg ]);
			}
			return $([]);
		}
		return xmlString;
	};
	
	// 현재시간을 ISO8601 형태로 반환
	getISODateTime = function() {
		var today = new Date();
		
		var year = today.getFullYear();
		var month = today.getMonth() + 1;
		var day = today.getDate();
		var hour = today.getHours();
		var hourUTC = today.getUTCHours();
		var diff = hour - hourUTC;
		var hourdifference = Math.abs(diff);
		var minute = today.getMinutes();
		var minuteUTC = today.getUTCMinutes();
		var minutedifference;
		var second = today.getSeconds();
		var timezone;
		
		if (minute != minuteUTC && minuteUTC < 30 && diff < 0) { 
			hourdifference--; 
		}
		if (minute != minuteUTC && minuteUTC > 30 && diff > 0) { 
			hourdifference--; 
		}
		if (minute != minuteUTC) {
			minutedifference = ":30";
		}
		else {
			minutedifference = ":00";
		}
		
		if (hourdifference < 10) { 
			timezone = "0" + hourdifference + minutedifference;
		}
		else {
			timezone = "" + hourdifference + minutedifference;
		}
		
		if (diff < 0) {
			timezone = "-" + timezone;
		}
		else {
			timezone = "+" + timezone;
		}
		if (month <= 9) 
			month = "0" + month;
		if (day <= 9) 
			day = "0" + day;
		if (hour <= 9) 
			hour = "0" + hour;
		if (minute <= 9) 
			minute = "0" + minute;
		if (second <= 9) 
			second = "0" + second;

		time = year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second + timezone;
		
		return time;

	};
	// 일,시,분을 ISO 8601 Duration 형태로 반환
	getISODuration = function(day, hour, minutes) {
		var isoDuration = "P";
		
		if (day > 0) isoDuration += day + "D";
		
		if ((hour > 0) || (minutes > 0)) {
			isoDuration += "T";
		    if (hour > 0) isoDuration += hour + "H";
		    if (minutes > 0) isoDuration += minutes + "M";
		}
		
		if (isoDuration == "P") isoDuration = "";

		return isoDuration;

	};
	//1DT1H1M  ==> {'1D', '1H', '1M'}
	getDateFromISODuration = function(isoDuration) {
		var dateArray = new Array();
		var result = isoDuration.match(/[0-9]*D|[0-9]*H|[0-9]*M/g);
		
		for(var i=0; i < result.length; i++) {
			dateArray[i] = result[i];
		}
		
		return dateArray;
	};
	// XML Util class
	XMLUtil.prototype = {
		/*
		 * method : nsResolver
		 * param 1 :
		 *     - nsPrefix (Character) : XPathEvaluator 의 인자값으로 들어
		 * return : namespaceURI
		 * Description : LGCNS XPDL Namespace Resolver
		 * */
		nsResolver : function(nsPrefix) {
			switch(nsPrefix) {
				case "payload" : return "http://bpm.oracle.co.kr/demo/MasterTypes";
				case "kospo" : return "http://www.kospo.go.kr";
				default : return "http://xmlns.oracle.com/bpel/workflow/task";
			}
		},
		getDocument : function() {
			return this.xmlDom;
		},
		clearDocument : function() {
			this.xmlDom = null;
		},
		/*
		 * method : nodePoint
		 * param 1 :
		 *     - idx (int) : xpath node pointer
		 * return : browser Fixed Point
		 * Description : Explorer 와 다른 브라우저간에 xpath point 위치를 맞추기 위함.
		 *               Node Start Point (explorer : 0, firefox : 1)
		 * */
		nodePoint : function(idx) {
			try {
				if(idx == 0)
					throw Error("node point [0]");
				else {
					if (window.ActiveXObject) {
						idx--;
					}
				}
			} catch (e)  {
				throw Error("[XMLUtil.nodePoint] "+e);
			}
			return idx;
		},
		/*
		 * method : createElement
		 * param 1 :
		 *     - appendTagname (Character) : 생성할 Element 의 Tag 명
		 *     - namespace (Character ) : 생성할 Element 의 네임스페이스
		 * return : Element
		 * Description : XMLUtil 객체가 가지고 있는 xmlDom Object 에 새로운 Element 추가
		 * */
		createElement : function(appendTagName, namespace) {
			//ie
			if (window.ActiveXObject) {
				var newElement = this.xmlDom.createNode(1, appendTagName, namespace);
			} else {
				var newElement = this.xmlDom.createElementNS(namespace, appendTagName);
			}
			
			return newElement;
		},
		
		/*
		 * method : selectSingleNode
		 * param 1 :
		 *     - elementPath (Character) : xpath
		 * return : node
		 * Description : XMLUtil 객체가 가지고 있는 xmlDom Object 에 존재하는 노드를 Xpath를 이용하여 찾아 반환
		 * */
		selectSingleNode : function(elementPath) {
			try {
				if(window.ActiveXObject)
			    {
					var v_regExp = new RegExp(replaceDefaultPrefix+":", "g");
					
			        return this.xmlDom.selectSingleNode(elementPath.replace(v_regExp, ""));
			    }
			    else
			    {
			       var xpe = new XPathEvaluator();
			       //var nsResolver = xpe.createNSResolver( this.xmlDom.ownerDocument == null ? this.xmlDom.documentElement : this.xmlDom.ownerDocument.documentElement);
			       var results = xpe.evaluate(elementPath,this.xmlDom,this.nsResolver,XPathResult.FIRST_ORDERED_NODE_TYPE, null);
			       return results.singleNodeValue; 
			    }
			} catch (e) {
				throw Error("[XMLUtil.selectSingleNode] "+e);
			}
			
		},
		
		/*
		 * method : selectNodes
		 * param 1 :
		 *     - elementPath (Character) : xpath
		 * return : nodeList
		 * Description : XMLUtil 객체가 가지고 있는 xmlDom Object 에 존재하는 노드를 Xpath를 이용하여 찾아 반환
		 * */
		selectNodes : function(elementPath) {
			try {
				if(window.ActiveXObject)
			    {
					var v_regExp = new RegExp(replaceDefaultPrefix+":", "g");
					
			    	return this.xmlDom.selectNodes(elementPath.replace(v_regExp, ""));
			    }
			    else
			    {
			    	var nodes=new Array;
			    	var xpe = new XPathEvaluator();
			        //var nsResolver = xpe.createNSResolver( this.xmlDom.ownerDocument == null ? this.xmlDom.documentElement : this.xmlDom.ownerDocument.documentElement);
			    	var results = xpe.evaluate(elementPath,this.xmlDom,this.nsResolver,XPathResult.ORDERED_NODE_ITERATOR_TYPE, null);
			       
			        if(results != null) {
			        	var element = results.iterateNext();
			        	while(element) {
			        		nodes.push(element);
			        		element = results.iterateNext();
			        	}
			        }
			        return nodes;
			    }
			} catch (e) {
				throw Error("[XMLUtil.selectNodes] "+e);
			}
			
		},
		/*
		 * method : appendChildElement
		 * param 1 :
		 *     - parentTagName (Character) : 추가할 Element의 상위 Tag 명
		 * 	   - idx : 상위 Tag 의 Node Point
		 *     - appendElement : 추가할 Element 객체
		 * return : void
		 * Description : XMLUtil 객체가 가지고 있는 xmlDom Object 에 Element 추가
		 * */
		appendChildElement : function(parentElement, appendElement) {
			//Append Append Element
			try {
				parentElement.appendChild(appendElement);
			} catch (e) {
				throw Error("[XMLUtil.appendChildElement] "+e);
			}
		},
		
		/*
		 * method : appendChildElementByTagName
		 * param 1 :
		 *     - parentTagName (Character) : 추가할 Element의 상위 Tag 명
		 * 	   - idx : 상위 Tag 의 Node Point
		 *     - namespace : 추가할 Element 의 네임스페이스
		 *     - appendTagName : 추가할 Element 의 Tag 명
		 * return : void
		 * Description : XMLUtil 객체가 가지고 있는 xmlDom Object 에 Element 추가
		 * */
		appendChildElementByTagName : function(parentElement, namespace, appendTagName) {
			try {
				var newElement = this.createElement(appendTagName, namespace);
				
				//Append Append Element
				parentElement.appendChild(newElement);
			} catch (e) {
				throw Error("[XMLUtil.appendChildElementByTagName] "+e);
			}
		},
		
		/*
		 * method : appendTextNode
		 * param 1 :
		 *     - currentTagName (Character) : 추가할 Element의 Tag 명
		 * 	   - idx : 추가할 Node Point
		 *     - text : 추가할 Text 값
		 * return : void
		 * Description : XMLUtil 객체가 가지고 있는 xmlDom Object 에 Element Tag 명으로 추가
		 * */
		appendTextNode : function(element, text) {
			try {
				if(text != '') {
					var textNode = this.xmlDom.createTextNode(text);
					
					element.appendChild(textNode);
				}
			} catch (e) {
				throw Error("[XMLUtil.appendTextNode] "+e);
			}
			
		}, 
		/*
		 * method : removeSingleNode
		 * param 1 :
		 *     - xpath (Character) : 삭제할 Xpath
		 * return : void
		 * Description : XMLUtil 객체가 가지고 있는 xmlDom Object 에 Xpath 에 해당하는 Node 삭제
		 * */
		removeSingleNode : function(xpath, isRemoveParent) {
			try {
				var currentNode = this.selectSingleNode(xpath);
				
				if(currentNode != null) {
					var parentNode = currentNode.parentNode;
					
					parentNode.removeChild(currentNode);
					
					// 하위 존재 하지 않으면 부모를 삭제한다.
					if(isRemoveParent && !parentNode.hasChildNodes()) {
						parentNode.parentNode.removeChild(parentNode);
					}
				}
			} catch (e) {
				throw Error("[XMLUtil.removeSingleNode] "+e);
			}
			
		},
		/*
		 * method : removeNodes
		 * param 1 :
		 *     - xpath (Character) : 삭제할 Xpath
		 * return : void
		 * Description : XMLUtil 객체가 가지고 있는 xmlDom Object 에 Xpath 에 해당하는 Multi Node 삭제
		 * */
		removeNodes : function(xpath, isRemoveParent) {
			try {
				var currentNodes = this.selectNodes(xpath);
				
				if(currentNodes.length > 0) {
					var parentNode = currentNodes[0].parentNode;
					for (var i=0; i < currentNodes.length; i++) {
						parentNode.removeChild(currentNodes[i]);
					}
					// 하위 존재 하지 않으면 부모를 삭제한다.
					if(isRemoveParent && !parentNode.hasChildNodes()) {
						parentNode.parentNode.removeChild(parentNode);
					}
				}
			} catch (e) {
				throw Error("[XMLUtil.removeNodes] "+e);
			}
		},
		/*
		 * method : createAttributeAndAppend
		 * param 1 :
		 *     - element (Element) : 추가할 Attribute 를 추가할 Element
		 *     - attName (Character) : 추가할 Attribute 명
		 *     - attValue (Character) : 추가할 Attribute 값
		 * return : void
		 * Description : XMLUtil 객체가 가지고 있는 xmlDom Object 에 존재하는 Element 안에 Attribute 객체 추가
		 *               Attribute 객체는 Element 객체 생성한 이후 바로 생성하므로, xpath가 아닌 Element 객체를 인자로 받는다.
		 * */
		createAttributeAndAppend : function(element, attName, attValue) {
			try {
				var newAttr = this.xmlDom.createAttribute(attName);
				newAttr.nodeValue = attValue;
				
				element.setAttributeNode(newAttr); 
		
				return element;
			} catch (e) {
				throw Error("[XMLUtil.createAttributeAndAppend] "+e);
			}
		},
		
		/*
		 * method : nodeCount
		 * param 1 :
		 *     - xpath (Character) : 확인할 노드의 xpath
		 * return : Node 갯수
		 * Description : XMLUtil 객체가 가지고 있는 xmlDom Object 에 Xpath 에 해당하는 노드의 갯수
		 * */
		nodeCount : function(xpath) {
			try {
				var count = "0";
				
				var nodes = this.selectNodes(xpath);
				
				return nodes.length;
			} catch (e) {
				throw Error("[XMLUtil.nodeCount] "+e);
			}
		}, 
		
		/*
		 * method : replaceChildNode
		 * param 1 :
		 *     - newNode (Node) : 변경할 Node
		 *     - oldNode (Node) : 변경 대상 Node
		 * return : Void
		 * Description : XMLUtil 객체가 가지고 있는 xmlDom Object 에 oldNode 를 newNode 로 변경함.
		 * */
		replaceChildNode : function(newNode, oldNode) {
			try {
				var parentNode = oldNode.parentNode;
			
				parentNode.replaceChild(newNode, oldNode);
			} catch(e) {
				throw Error("[XMLUtil.replaceChildNode] "+e);
			}
		},
		
		/*
		 * method : replaceChildXpathNode
		 * param 1 :
		 *     - newNode (Node) : 변경할 Node
		 *     - oldXpath (Character) : 변경 대상 Node 의 xpath
		 * return : Void
		 * Description : XMLUtil 객체가 가지고 있는 xmlDom Object 에 oldNode(xpath) 를 newNode 로 변경함.
		 * */
		replaceChildXpathNode : function(newNode, oldXpath) {
			try {
				// replace 대상 node 존재 여부 (1개)
				if(this.nodeCount(oldXpath) == 1) {
					var oldNode = this.selectSingleNode(oldXpath);
					
					this.replaceChildNode(newNode, oldNode);
					
				} else if (this.nodeCount(oldXpath) > 1) {
					var oldNodes = this.selectNodes(oldXpath);
					
					for(var i=0; i < oldNodes.length; i++) {
						this.replaceChildNode(newNode, oldNodes[i]);
					}
				} else {
					throw Error("required Exist Old Node!");
				}
			} catch(e) {
				throw Error("[XMLUtil.replaceChildXpathNode] "+e);
			}
		}, 
		
		/*
		 * method : setNodesValue
		 * param 1 :
		 *     - xpathNode (Character) : 변경할 Node
		 *     - value (Character) : 변경할 값
		 * return : Void
		 * Description : XMLUtil 객체가 가지고 있는 xmlDom Object 에 기존 Node 의 값을 변경함.
		 *               Node 유형이 Element(1), Attribute(2), Text(3) 일 경우 모두 가능함.
		 * */
		setNodesValue : function(xpathNode, value) {
			var nodes = this.selectNodes(xpathNode);
			try {
				if(nodes.length > 0) {
					
					for(var i=0; i < nodes.length; i++) {
						if(nodes[i].nodeType == 1) {  		// Element
							if(nodes[i].hasChildNodes()) {
								if(nodes[i].firstChild.nodeType == 3) {	// Text Node
									nodes[i].firstChild.nodeValue = value;
								}
							} else {
								var textNode = this.xmlDom.createTextNode(value);
								
								nodes[i].appendChild(textNode);
							}
						} else if(nodes[i].nodeType == 2) { // Attribute
							nodes[i].nodeValue = value;
						} else if(nodes[i].nodeType == 3) { // Text
							nodes[i].nodeValue = value;
						}
					}
				} else {
					throw Error("node count [0]");
				}
			} catch (e) {
				throw Error("[XMLUtil.setNodesValue] "+e);
			}
		},
		/*
		 * method : setNodeValue
		 * param 1 :
		 *     - xpathNode (Character) : 변경할 Node
		 *     - value (Character) : 변경할 값
		 * return : Void
		 * Description : XMLUtil 객체가 가지고 있는 xmlDom Object 에 기존 Node 의 값을 변경함.
		 *               Node 유형이 Element(1), Attribute(2), Text(3) 일 경우 모두 가능함.
		 * */
		setNodeValue : function(xpathNode, value) {
			var node = this.selectSingleNode(xpathNode);
			
			try {
				if(node != null) {
					if(node.nodeType == 1) {  		// Element
						if(node.hasChildNodes()) {
							if(node.firstChild.nodeType == 3) {	// Text Node
								node.firstChild.nodeValue = value;
							}
						} else {
							var textNode = this.xmlDom.createTextNode(value);
							
							node.appendChild(textNode);
						}
					} else if(node.nodeType == 2) { // Attribute
						node.nodeValue = value;
					} else if(node.nodeType == 3) { // Text
						node.nodeValue = value;
					}
				} else {
					throw Error("node is null");
				}
			} catch (e) {
				throw Error("[XMLUtil.setNodeValue] "+e);
			}
		},
		/*
		 * method : setElementNodeValue
		 * param 1 :
		 *     - element (Element) : 추가할 Element
		 *     - text : 추가할 Text 값
		 * return : void
		 * Description : Element에 text 추가
		 * */
		setElementNodeValue : function(element, text) {
			try {
				if(element.hasChildNodes()) {
					if(element.firstChild.nodeType == 3) {	// Text Node
						element.firstChild.nodeValue = text;
					}
				} else {
					var textNode = this.xmlDom.createTextNode(text);
					
					element.appendChild(textNode);
				}
			} catch (e) {
				throw Error("[XMLUtil.setElementNodeValue] "+e);
			}
		}
	};
})(jQuery);




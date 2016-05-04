
// * Edit to suit your needs.
var ADAPT_CONFIG = {
  // Where is your CSS?
  path: 'resources/skin/css/',


  // false = Only run once, when page first loads.
  // true = Change on window resize and page tilt.
  dynamic: true,

  // First range entry is the minimum.
  // Last range entry is the maximum.
  // Separate ranges by "to" keyword.
  range: [
    '0px    to 760px  = 760.min.css', // mobile
    '760px  to 1024px  = 1024.min.css', // ipad
    '1024px to 3000px = 1200.min.css', // web
  ]
};

// jquery
$(function(){

	Stopen(); // sitemap open close
	gnb(); // gnb contarol
	list();
	list2();
	board_01();
	lnb(); // lnb control
	thumbnail_sort();
	btn_layer();
	processpop();
	typeoption();
	hvtr();
	tabmenu(); // tabmenu
	bgreser();
	selth();
	stateopen();
	mapclick();
	imgrollover();

});

var imgrollover = function()
{
	$("img.rollover").hover( 
	function() { this.src = this.src.replace("_off", "_on"); 
	}, 
	function() { this.src = this.src.replace("_on", "_off"); 
	});
}

var mapclick = function()
{

	$(".overlink > a").click(function(){
		$(".overlink > a").removeClass("on");
		$(this).addClass("on");
		return false;
	});

	$(".map_close").click(function(){
		$(".layer_map").hide();
		return false;
	});

	$(".click_map01").click(function(){
		$(".layer_map.europe").show();
		return false;
	});

	$(".click_map02").click(function(){
		$(".layer_map.china").show();
		return false;
	});

	$(".click_map03").click(function(){
		$(".layer_map.asia").show();
		return false;
	});

	$(".click_map04").click(function(){
		$(".layer_map.korea").show();
		return false;
	});

	$(".click_map05").click(function(){
		$(".layer_map.americas").show();
		return false;
	});

	$(".click_map01").hover(function(){
		$(".map_01").hide();
		$(".map_02").show();
	}, function(){
		$(".map_01").show();
		$(".map_02").hide();
	});

	$(".click_map02").hover(function(){
		$(".map_01").hide();
		$(".map_03").show();
	}, function(){
		$(".map_01").show();
		$(".map_03").hide();
	});

	$(".click_map03").hover(function(){
		$(".map_01").hide();
		$(".map_04").show();
	}, function(){
		$(".map_01").show();
		$(".map_04").hide();
	});

	$(".click_map04").hover(function(){
		$(".map_01").hide();
		$(".map_05").show();
	}, function(){
		$(".map_01").show();
		$(".map_05").hide();
	});

	$(".click_map05").hover(function(){
		$(".map_01").hide();
		$(".map_06").show();
	}, function(){
		$(".map_01").show();
		$(".map_06").hide();
	});
}

var stateopen = function()
{
	$(".state_close").click(function(){
		$(this).hide();
		$(this).next().show();
		return false;
	});

	$(".state_open").click(function(){
		$(this).hide();
		$(this).prev().show();
		return false;
	});
}

var selth = function()
{
	$(".selectbox_th dl dd a").click(function(){
		$(".selectbox_th dl dd a").removeClass("on");
		$(this).addClass("on");
		return false;
	});

	$(".selectbox_th dl dd a").mouseover(function(){
		$(".selectbox_th dl dd a span").hide();
		$(this).find(">span").show();
		$(".selectbox_th dl dd a").removeClass("on");
		$(this).addClass("on");
	});

	$(".selectbox_th dl dd a").mouseleave(function(){
		$(".selectbox_th dl dd a span").hide();
		$(this).find(">span").hide();
		$(".selectbox_th dl dd a").removeClass("on");
	});
}

var bgreser = function()
{
	$(".bg_reser").hover(function(){
		var reser = $(this).find(".layer_day").css("display");
		if ( reser == "none" ){
			$(".bg_reser .layer_day").hide();
			$(this).find(".layer_day").show();
		} else {
			$(".bg_reser .layer_day").hide();
			$(this).find(".layer_day").hide();
		}
	});
}

var tabmenu = function()
{
	$(".tabmenu ul li").click(function(){
		$(".tabmenu ul li").removeClass("on");
		$(this).addClass("on");
		return false;
	});

	$(".tabmenu2 li").click(function(){
		$(".tabmenu2 li").removeClass("on");
		$(this).addClass("on");
		return false;
	});

	$(".tabmenu3 li").click(function(){
		$(".tabmenu3 li").removeClass("on");
		$(this).addClass("on");
		return false;
	});
}

var hvtr = function()
{
	$(".txt_list > li").click(function(){
		$(this).toggleClass("on");
	});
}

var typeoption = function()
{
	$(".type_modify ul li").click(function(){
		$(this).toggleClass("on");
	});
}

var processpop = function()
{
	$(".board_system td li").hover(
		function(){
			$(this).find(">div").show();
		}, function(){
			$(this).find(">div").hide();
		}
	);
}

var btn_layer = function()
{
	$(".layer_show1").click(function(){
		$(".showpop").fadeIn();
		$(".deem").fadeIn();
	});

	$(".layer_show2").click(function(){
		$(".showpop2").fadeIn();
		$(".deem").fadeIn();
	});

	$(".btn_pop_close").click(function(){
		$(".showpop").fadeOut();
		$(".showpop2").fadeOut();
		$(".deem").fadeOut();
	});
}

var thumbnail_sort = function()
{
	$(".thumbnail_sort").click(function(){
		$(this).find("div").toggle();
	});
}

var lnb = function()
{

	$(".left_navigation > li > a").click(function(){
		$(this).parent().toggleClass("on");
		$(this).parent().find(">ul").toggle();
		return false;
	});

	$("#lnb .left_navigation > li > ul > li > ul > li > a").click(function(){
		$(this).parent().toggleClass("on");
		return false;
	});

	/*
	$(".left_navigation > li").mouseleave(function(){
		$(".left_navigation > li").removeClass("on");
		$(".left_navigation > li > ul").stop(true, true).slideUp();
	});
	*/
}

var list = function()
{
	$(".boxmodel_11 > div > a").click(function(){
		$(this).toggleClass("list_on");
		$(this).prev().slideToggle();
	});
}

var list2 = function()
{
	$(".boxmodel_12 > div > a").click(function(){
		$(this).toggleClass("list_on");
		$(this).prev().slideToggle();
	});
}

var gnb = function(){
	$(".gnb > li").mouseover(function(){
		$(".gnb > li").removeClass("on");
		$(".gnb > li > div").hide();
		$(this).addClass("on");
		$(this).find(" > div ").show();
	});

	$(".gnb > li").mouseleave(function(){
		$(".gnb > li > div").hide();
		$(".gnb > li").removeClass("on");
	});
}

var Stopen = function()
{
	$(".btn_close").click(function(){
		$(this).hide();
		$(".btn_open").show();
		$(".footer_sitemap > ul").animate({
			"height" : "40px"
		});
	});

	$(".btn_open").click(function(){
		$(".footer_sitemap > ul").animate({
			"height" : "210px"
		});
		$(this).hide();
		$(".btn_close").show();
	});
}

var board_01 = function()
{
	$(".board_01 tbody tr:odd").css("background", "#f7f7f7");
	$(".board_02 tbody tr:odd").css("background", "#f7f7f7");
}
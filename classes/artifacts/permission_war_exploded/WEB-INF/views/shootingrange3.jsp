<%--
  Created by IntelliJ IDEA.
  User: songwenming
  Date: 2020/1/26
  Time: 19:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>训练准备</title>
    <jsp:include page="/common/backend_common.jsp"/>
    <jsp:include page="/common/page.jsp"/>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>
<div class="page-header">
    <h1>
        开始打靶
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            参训人员按照分组开始打靶
        </small>
    </h1>
</div>
<div class="container" id="container" >

</div>

<script id="shootingRangeDataTemplate" type="x-tmpl-mustache">
     <div class="_top">
        <div class="_top-left">
            <div class="_singles-parents">
            {{#traineeShooting_deviceGroup_data_list}}
                <div class="_single">
                    <div class="border-wording">{{deviceGroupIndex}}号靶</div>
                    <div class="_img-parent" id="_goals{{deviceGroupIndex}}">
                        <img src="/assets/images/icon-msr.png"/>
                    </div>

                    <div class="person_info">
                        <div class="_info-img">
                            <img src={{showPhoto}}>
                        </div>
                        <div class="_info-list">
                            <span>{{name}}<span　class="trainee_status" id="trainee_{{deviceGroupIndex}}" style="float:right">{{showTraineeStatus}}</span></span>
                            <span class="target_status" id="target_{{deviceGroupIndex}}">靶机：{{showTargetStatus}}</span>
                            <span class="camera_status" id="camera_{{deviceGroupIndex}}">采集：{{showCameraStatus}}</span>
                            <span class="display_status" id="display_{{deviceGroupIndex}}">显靶：{{showDisplayStatus}}</span>
                        </div>
                    </div>
                    <div class="person-score" id="_scoretableparent{{deviceGroupIndex}}">
                        <table border="1">
                            <tr>
                             {{#shootingScoreList}}
                                <th>{{scoreIndex}}</th>
                             {{/shootingScoreList}}
                                <th class="score-sum">总分</th>
                            </tr>
                            <tr>
                             {{#shootingScoreList}}
                                <td>{{ringnumber}}</td>
                             {{/shootingScoreList}}
                                <td>{{totalScore}}</td>
                            </tr>
                        </table>
                    </div>
                </div>
                {{/traineeShooting_deviceGroup_data_list}}
            </div>
        </div>
        <div class="_top-right">
            <span style="font-size: 8pt;margin-left: 25px;">控制台</span>
            <div class="_right-bottom">
                <div class="right-time">
                    <div id="clock" style="float:left;margin-left:5px;margin-top:3px">
                        <div id="hour"></div>
                        <div id="min"></div>
                        <div id="sec"></div>
                        <div id="point"></div>
                        <ul id="mycircle" style="margin-left:0px"></ul>
                    </div>
                    <div id="date-week" style="float:right;margin-right:5px;margin-top:3px">
                       <div id="mydate" style="color:white">
                       2019-12-12
                       </div>
                       <div id="myweekday" style="text-align:center;color:white">
                       星期一
                       </div>
                    </div>
                    <div id="mytime" style="float:right; line-height:40px;margin-right:5px;font-size:13pt;color:white;margin-top:3px">10:24:46</div>
                </div>
				<div class="right-click">
					<div style="float:left">
					    <div class="click-img">
						    <a href="#">
							    <img src="/assets/images/icon-r.png"/>
						    </a>
					    </div>
					    <span>更换靶纸</span>
                    </div>
					<div style="float:left">
						<div class="click-img">
                            <a href="javascript:deviceDetection()">
								<img src="/assets/images/icon-r.png"/>
							</a>
						</div>
						<span>靶位检测</span>
					</div>
					<div style="float:left">
						<div class="click-img">
						    <a href="javascript:changeShootingTrainee()">
								<img src="/assets/images/icon-r.png"/>
							</a>
						</div>
						<span>更迭射击人员</span>
					</div>
					<div style="float:left">
						<div class="click-img">
						    <a href="javascript:startShooting()">
								<img src="/assets/images/icon-r.png"/>
							</a>
						</div>
						<span>开始射击</span>
					</div>
					<div style="float:left">
						<div class="click-img">
						    <a href="javascript:endShooting()">
								<img src="/assets/images/icon-r.png"/>
							</a>
						</div>
						<span>结束射击</span>
					</div>
					<div style="float:left">
						<div class="click-img">
						    <a href="/sys/trainee/traineegroupquery.page">
								<img src="/assets/images/icon-r.png"/>
							</a>
						</div>
						<span>调整靶位编组</span>
					</div>

					<div style="float:left">
						<div class="click-img">
						    <a href="javascript:sendTestData()">
								<img src="/assets/images/icon-r.png"/>
							</a>
						</div>
						<span>发送测试数据</span>
					</div>
					<div style="clear:both;"></div>
				</div>
                <div style="width:200px;height:80px;">
                </div>
                <div style="margin-left:5px;">下一组:</div>
                <div style="margin-left:5px;" class="next-group">{{nextGroupTrainee}}</div>

                <div class="bottom-bottom">
                    <div class="bottom-left">
                        当前编组：第{{currentTrainneeGroup}}组，共{{sumOfTraineeGroup}}组<br/>
                        本次训练计划：<br/>
                        共{{sumOfTrainee}}人<br/>
                        已完成：{{traineeCountFinish}}人<br/>
                        未完成：{{traineeCountNotFinish}}人<br/>
                        缺席：{{traineeCountAbsent}}人
                    </div>
                    <div class="bottom-right">
                        <div id="rate" style="height:100%;width:100%"></div>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <div class="_bottom">
        <div class="_bottom-top">消息区:</div>
        <div class="_bottom-font">
            1号靶三正常<br/>
            2号靶李四正常<br/>
            3号靶机故障
        </div>
    </div>
</script>
<script id="scoreTemplate" type="x-tmpl-mustache">
     {{#scoreList}}
     <div class="_goals" style="left:{{px}}px;top:{{py}}px;"></div>
     {{/scoreList}}
</script>

<script id="scoreTableTemplate" type="x-tmpl-mustache">
<table border="1">
    <tr>
        {{#scoreList}}
        <th>{{id}}</th>
        {{/scoreList}}
        <th class="score-sum">总分</th>
    </tr>
    <tr>
        {{#scoreList}}
        <td>{{score}}</td>
        {{/scoreList}}
        <td>{{totalScore}}</td>
    </tr>
</table>
</script>
<script type="application/javascript">
    $(function() {
        var shootingRangeDataTemplate = $('#shootingRangeDataTemplate').html();
        Mustache.parse(shootingRangeDataTemplate);
        var scoreTemplate = $('#scoreTemplate').html();
        Mustache.parse(scoreTemplate);
        loadShootingRangeData();

        $('#_scoretableparent1').scrollRight( $('#_scoretableparent1')[0].scrollWidth);

        function loadShootingRangeData(){
            var url = "/sys/shootingrange/shootingrangedata.json";
            $.ajax({
                url : url,

                success: function (result) {
                    if (result.ret) {
                        var rendered = Mustache.render(shootingRangeDataTemplate,{
                            traineeShooting_deviceGroup_data_list:result.data.traineeShooting_deviceGroup_data,
                            nextGroupTrainee:result.data.nextGroupTraineeNames,
                            currentTrainneeGroup:result.data.currentTrainneeGroupIndex,
                            sumOfTraineeGroup:result.data.sumOfTraineeGroupCount,
                            sumOfTrainee:result.data.sumOfTraineeCount,
                            traineeCountFinish:result.data.traineeCountFinishShooting,
                            traineeCountNotFinish:result.data.traineeCountNotShooting,
                            traineeCountAbsent:result.data.traineeCountAbsentShooting,
                            "showPhoto":function () {
                                if(this.photo==null)
                                {
                                    return "/assets/images/icon-person.png";
                                }
                                else
                                {
                                    return this.photo;
                                }
                            },
                            "showTraineeStatus": function() {
                                if(this.name==null)
                                {
                                    return null;
                                }
                                else
                                {
                                    return this.traineeStatus == 0 ? '：等候打靶' : (this.traineeStatus == 1 ? '：正常登陆' : (this.traineeStatus == 2 ? '：未登陆' : (this.traineeStatus == 3 ? '：打靶中' : (this.traineeStatus == 4 ? '：打靶完毕' : "：缺席"))));
                                }
                            },
                            "showDisplayStatus":function () {
                                return this.displayStatus == 0 ? '正常' : (this.displayStatus == 1 ? '异常' : "新添加");

                            },
                            "showCameraStatus":function () {
                                //默认采靶终端是正常，直接返回正常状态
                                // return this.cameraStatus == 0 ? '正常' : (this.cameraStatus == 1 ? '异常' : "新添加");
                                return '正常';
                            },
                            "showTargetStatus":function () {
                                return this.targetStatus == 0 ? '正常' : (this.targetStatus == 1 ? '异常' : "新添加");
                            }



                        });
                        $("#container").html(rendered);
                        setLeftWidth();//动态设置界面宽度，bamian
                        setTableWidth();
                        showScores();
                        function showScores () {
                            var  diameter=150;//._img-parent为靶图的父元素为正方形宽高度为150，靶图无论大小都被限制为150px见方大小，
                            var　bullsEyeRadius=150/10;//靶心半径为靶图直径的１０分之一
                            //var  targetBullsEyeRadius=this.radius;
                            //var radio=FloatDiv(bullsEyeRadius,targetBullsEyeRadius);
                            var bullsEye_X=FloatDiv(diameter,2)//靶心ｘ坐标
                            var bullsEye_Y=FloatMul(diameter,0.6)//
                            var deviceGroup_data_list=result.data.traineeShooting_deviceGroup_data//所有靶位的数据等同traineeShooting_deviceGroup_data_list
                            <!--开始把数组中回传数据的px,py即x,y坐标根据两端坐标比例进行变换,在存回去,供下一步解析模板使用-->
                            for (var m=0;m<deviceGroup_data_list.length;m++)
                            {
                                //取得一个靶位的数据值
                                var deviceGroupDataList=deviceGroup_data_list[m];
                                var shootingScoreList=deviceGroupDataList.shootingScoreList;
                                if(shootingScoreList!=null) {
                                    for (var n = 0; n < shootingScoreList.length; n++) {

                                        var score = deviceGroupDataList.shootingScoreList[n];
                                        var radius = score.radius;//传回来的显靶终端靶心半径以px为单位
                                        // var radius_mm=score.mmOfRadius;////传回来的显靶终端靶心半径以mm为单位,，暂时没有用因为服务器端用的的以px为单位
                                        var px = score.px;//取得回传的x
                                        var py = score.py;//取得回传的y
                                        var radio = FloatDiv(bullsEyeRadius, radius);
                                        var X = bullsEye_X + px * radio;//返回的x以左为负数
                                        var Y = bullsEye_Y + py * radio;//返回的y以下为正
                                        deviceGroup_data_list[m].shootingScoreList[n].px = X-1.5;
                                        deviceGroup_data_list[m].shootingScoreList[n].py = Y-1.5;
                                    }
                                }
                            }
                            <!--结束把数组中回传数据的px,py即x,y坐标根据两端坐标比例进行变换,在存回去,供下一步解析模板使用-->

                            for(var i=0;i<deviceGroup_data_list.length;i++){
                                var rendered = Mustache.render(scoreTemplate, {scoreList:deviceGroup_data_list[i].shootingScoreList});

                                $("#_goals"+deviceGroup_data_list[i].deviceGroupIndex).append(rendered);//注意这里是append,不是html(html是覆盖,append是添加)
                            }
                        }
                        <!--开始显示时间栏右侧的时间日期和星期几-->
                        setInterval(updateTime, 1000);
                        updateTime();
                        function updateTime() {
                            var cd = new Date();
                            var myweek = ['星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
                            var mytime = zeroPadding(cd.getHours(), 2) + ':' + zeroPadding(cd.getMinutes(), 2) + ':' + zeroPadding(cd.getSeconds(), 2);

                            var mydate = zeroPadding(cd.getFullYear(), 4) + '-' + zeroPadding(cd.getMonth()+1, 2) + '-' + zeroPadding(cd.getDate(), 2);

                            var myday=cd.getDay();

                            var myweekday= myweek[myday];
                            $("#mytime").html(mytime);
                            $("#mydate").html(mydate);
                            $("#myweekday").html(myweekday);
                        };
                        <!--结束显示时间栏右侧的时间日期和星期几-->
                        <!--开始显示时钟-->
                        var hourDom=document.getElementById('hour');
                        var minDom=document.getElementById('min');
                        var secDom=document.getElementById('sec');
                        var cricle=document.getElementById('mycircle');
                        //创建表盘，ul宽高为wrap宽高，以wrap中心点为变换基点，动态分配6°的li
                        for (var i=0;i<60;i++) {
                            var li=document.createElement('li');
                            cricle.appendChild(li);
                            if(i%5==0){
                                li.style.height="6px";
                                li.style.width="1px";
                            }
                            li.style.transform='rotate('+i*6+'deg)';
                        }

                        setInterval(function(){
                            var date=new Date();
                            var hour=date.getHours();
                            var min=date.getMinutes();
                            var sec=date.getSeconds();
                            min+=sec/60;
                            hour+=min/60;

                            //当前时间*每个单位时间走的角度=指针指向
                            hourDom.style.transform='rotate('+hour*30+'deg)';
                            minDom.style.transform='rotate('+min*6+'deg)';
                            secDom.style.transform='rotate('+sec*6+'deg)';
                        },1000);
                        <!--结束显示时钟-->

                        <!--开始显示echart-->
                        var dom = document.getElementById("rate");
                        var myChart = echarts.init(dom);
                        option = {
                            tooltip: {
                                trigger: 'item',
                                formatter: '{a} <br/>{b}: {c} ({d}%)'
                            },
                            itemStyle: {
                                normal:{
                                    //自定义颜色
                                    color:function(params) {
                                        //自定义颜色
                                        var colorList = [
                                            '#41C7DB','#DDDDDD'
                                        ];
                                        return colorList[params.dataIndex];
                                    }
                                }
                            },
                            series: [
                                {
                                    name: '访问来源',
                                    type: 'pie',
                                    radius: ['80%', '100%'],
                                    avoidLabelOverlap: false,
                                    label: {
                                        normal: {
                                            show: true,
                                            position: 'center',
                                            formatter: function(data){ // 设置圆饼图中间文字排版
                                                if(data.name=="打靶完毕"){
                                                    return data.percent +"%";
                                                }
                                                return "";
                                            }
                                        },
                                        emphasis: {
                                            show: true,
                                            textStyle: {
                                                fontSize: '15',
                                                fontWeight: 'bold'
                                            }
                                        }
                                    },
                                    labelLine: {
                                        normal: {
                                            show: false
                                        }
                                    },
                                    data: [
                                        {value: result.data.traineeCountFinishShooting, name: '打靶完毕'},
                                        {value: result.data.traineeCountNotShooting, name: '未打靶'}
                                    ]
                                }
                            ]
                        };
                        myChart.setOption(option);
                        <!--结束显示echart-->
                        /* showMessage("分组成功", JSON.stringify(result.data)+"", true);*/

                    } else {
                        showMessage("分组失败", result.msg, false);
                    }

                }
            })
        }
    });
    function setTableWidth(){
        var cols = $(".person-score table th").size();
        $(".person-score table").attr("width",170);
    }



    function setBarPosition(){

        $("._single ._img-parent").append("<div class=\"_goals\" style=\"left:55px;top:80px;\"><span class=\"_number\">3</span></div>");
    }
    //setBarPosition();

    function setLeftWidth(){
        var width=$("body").width();
        $("._top-left").css({"width":width-240-55+"px"})
    }



    function zeroPadding(num, digit) {
        var zero = '';
        for(var i = 0; i < digit; i++) {
            zero += '0';
        }
        return (zero + num).slice(-digit);
    };

    function FloatDiv(arg1,arg2){
        var t1=0,t2=0,r1,r2;
        try{t1=arg1.toString().split(".")[1].length}catch(e){}
        try{t2=arg2.toString().split(".")[1].length}catch(e){}
        with(Math){
            r1=Number(arg1.toString().replace(".",""));
            r2=Number(arg2.toString().replace(".",""));
            return (r1/r2)*pow(10,t2-t1);
        }
    };
    function FloatMul(arg1,arg2)
    {
        var m=0,s1=arg1.toString(),s2=arg2.toString();
        try{m+=s1.split(".")[1].length}catch(e){}
        try{m+=s2.split(".")[1].length}catch(e){}
        return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
    }
    function FloatAdd(arg1,arg2){
        var r1,r2,m;
        try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
        try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
        m=Math.pow(10,Math.max(r1,r2));
        return (arg1*m+arg2*m)/m;
    }
    function FloatSub(arg1,arg2){
        var r1,r2,m,n;
        try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
        try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
        m=Math.pow(10,Math.max(r1,r2));
        //动态控制精度长度
        n=(r1=r2)?r1:r2;
        return ((arg1*m-arg2*m)/m).toFixed(n);
    }


    //更迭射击人员
    function changeShootingTrainee()
    {

        var shootingRangeDataTemplate = $('#shootingRangeDataTemplate').html();
        Mustache.parse(shootingRangeDataTemplate);
        var scoreTemplate = $('#scoreTemplate').html();
        Mustache.parse(scoreTemplate);
        var finishShooting=true;
        //在更迭射击人员前要判断一下是否还有未完成射击的人员，如有则须确定是否继续
        $("._info-list").each(function(){
            var info = $(this).find("span").text();
            if(info.indexOf("打靶完毕")==-1){
                finishShooting=false;
            }
        });
        if(finishShooting) {
            if(confirm('所有靶位已经完成射击，确定要更迭射击人员吗')==true){
                $.ajax({
                    url: "/sys/shootingrange/changeShootingTrainee.json",
                    contentType: "application/json",
                    success: function (result) {
                        if (result.ret) {
                            var rendered = Mustache.render(shootingRangeDataTemplate, {
                                traineeShooting_deviceGroup_data_list: result.data.traineeShooting_deviceGroup_data,
                                nextGroupTrainee: result.data.nextGroupTraineeNames,
                                currentTrainneeGroup: result.data.currentTrainneeGroupIndex,
                                sumOfTraineeGroup: result.data.sumOfTraineeGroupCount,
                                sumOfTrainee: result.data.sumOfTraineeCount,
                                traineeCountFinish: result.data.traineeCountFinishShooting,
                                traineeCountNotFinish: result.data.traineeCountNotShooting,
                                traineeCountAbsent: result.data.traineeCountAbsentShooting,
                                "showPhoto": function () {
                                    if (this.photo == null) {
                                        return "/assets/images/icon-person.png";
                                    }
                                    else {
                                        return this.photo;
                                    }
                                },
                                "showTraineeStatus": function () {
                                    if (this.name == null) {
                                        return null;
                                    }
                                    else {
                                        return this.traineeStatus == 0 ? '：等候打靶' : (this.traineeStatus == 1 ? '：正常登陆' : (this.traineeStatus == 2 ? '：未登陆' : (this.traineeStatus == 3 ? '：打靶中' : (this.traineeStatus == 4 ? '：打靶完毕' : "：缺席"))));
                                    }
                                },
                                "showDisplayStatus": function () {
                                    return this.displayStatus == 0 ? '未知' : (this.displayStatus == 1 ? '正常' : "异常");

                                },
                                "showCameraStatus": function () {
                                    return this.cameraStatus == 0 ? '未知' : (this.cameraStatus == 1 ? '正常' : "异常");
                                },
                                "showTargetStatus": function () {
                                    return this.targetStatus == 0 ? '未知' : (this.targetStatus == 1 ? '正常' : "异常");
                                }


                            });
                            $("#container").html(rendered);
                            setLeftWidth();//动态设置界面宽度，bamian
                            setTableWidth();
                            showScores();

                            function showScores() {
                                var diameter = 150;//._img-parent为靶图的父元素为正方形宽高度为150，靶图无论大小都被限制为150px见方大小，
                                var bullsEyeRadius = 150 / 10;//靶心半径为靶图直径的１０分之一
                                //var  targetBullsEyeRadius=this.radius;
                                //var radio=FloatDiv(bullsEyeRadius,targetBullsEyeRadius);
                                var bullsEye_X = FloatDiv(diameter, 2)//
                                var bullsEye_Y = FloatMul(diameter, 0.6)//
                                var deviceGroup_data_list = result.data.traineeShooting_deviceGroup_data//一个靶位数据：同traineeShooting_deviceGroup_data_list
                                <!--开始把数组中回传数据的px,py即x,y坐标根据两端坐标比例进行变换,在存回去,供下一步解析模板使用-->
                                for (var m = 0; m < deviceGroup_data_list.length; m++) {
                                    var deviceGroupDataList = deviceGroup_data_list[m];
                                    var shootingScoreList = deviceGroupDataList.shootingScoreList;
                                    if (shootingScoreList != null) {
                                        for (var n = 0; n < shootingScoreList.length; n++) {

                                            var score = deviceGroupDataList.shootingScoreList[n];
                                            var radius = score.radius;//传回来的显靶终端靶心半径以px为单位
                                            // var radius_mm=score.mmOfRadius;////传回来的显靶终端靶心半径以mm为单位,，暂时没有用因为服务器端用的的以px为单位
                                            var px = score.px;//取得回传的x
                                            var py = score.py;//取得回传的y
                                            var radio = FloatDiv(bullsEyeRadius, radius);
                                            var X = bullsEye_X + px * radio;//返回的x以左为负
                                            var Y = bullsEye_Y + py * radio;//返回的y以下为正
                                            deviceGroup_data_list[m].shootingScoreList[n].px = X-1.5;
                                            deviceGroup_data_list[m].shootingScoreList[n].py = Y-1.5;
                                        }
                                    }
                                }
                                <!--结束把数组中回传数据的px,py即x,y坐标根据两端坐标比例进行变换,在存回去,供下一步解析模板使用-->

                                for (var i = 0; i < deviceGroup_data_list.length; i++) {
                                    var rendered = Mustache.render(scoreTemplate, {scoreList: deviceGroup_data_list[i].shootingScoreList});
                                    $("#_goals" + deviceGroup_data_list[i].deviceGroupIndex).append(rendered);//注意这里是append,不是html(html是覆盖,append是添加)
                                }
                            }

                            <!--开始显示时间栏右侧的时间日期和星期几-->
                            setInterval(updateTime, 1000);
                            updateTime();

                            function updateTime() {
                                var cd = new Date();
                                var myweek = ['星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
                                var mytime = zeroPadding(cd.getHours(), 2) + ':' + zeroPadding(cd.getMinutes(), 2) + ':' + zeroPadding(cd.getSeconds(), 2);
                                var mydate = zeroPadding(cd.getFullYear(), 4) + '-' + zeroPadding(cd.getMonth() + 1, 2) + '-' + zeroPadding(cd.getDate(), 2);
                                var myday = cd.getDay();
                                var myweekday = myweek[myday];
                                $("#mytime").html(mytime);
                                $("#mydate").html(mydate);
                                $("#myweekday").html(myweekday);
                            };
                            <!--结束显示时间栏右侧的时间日期和星期几-->
                            <!--开始显示时钟-->
                            var hourDom = document.getElementById('hour');
                            var minDom = document.getElementById('min');
                            var secDom = document.getElementById('sec');
                            var cricle = document.getElementById('mycircle');
                            //创建表盘，ul宽高为wrap宽高，以wrap中心点为变换基点，动态分配6°的li
                            for (var i = 0; i < 60; i++) {
                                var li = document.createElement('li');
                                cricle.appendChild(li);
                                if (i % 5 == 0) {
                                    li.style.height = "6px";
                                    li.style.width = "1px";
                                }
                                li.style.transform = 'rotate(' + i * 6 + 'deg)';
                            }

                            setInterval(function () {
                                var date = new Date();
                                var hour = date.getHours();
                                var min = date.getMinutes();
                                var sec = date.getSeconds();
                                min += sec / 60;
                                hour += min / 60;

                                //当前时间*每个单位时间走的角度=指针指向
                                hourDom.style.transform = 'rotate(' + hour * 30 + 'deg)';
                                minDom.style.transform = 'rotate(' + min * 6 + 'deg)';
                                secDom.style.transform = 'rotate(' + sec * 6 + 'deg)';
                            }, 1000);
                            <!--结束显示时钟-->

                            <!--开始显示echart-->
                            var dom = document.getElementById("rate");
                            var myChart = echarts.init(dom);
                            option = {
                                tooltip: {
                                    trigger: 'item',
                                    formatter: '{a} <br/>{b}: {c} ({d}%)'
                                },
                                itemStyle: {
                                    normal: {
                                        //自定义颜色
                                        color: function (params) {
                                            //自定义颜色
                                            var colorList = [
                                                '#41C7DB', '#DDDDDD'
                                            ];
                                            return colorList[params.dataIndex];
                                        }
                                    }
                                },
                                series: [
                                    {
                                        name: '访问来源',
                                        type: 'pie',
                                        radius: ['80%', '100%'],
                                        avoidLabelOverlap: false,
                                        label: {
                                            normal: {
                                                show: true,
                                                position: 'center',
                                                formatter: function (data) { // 设置圆饼图中间文字排版
                                                    if (data.name == "打靶完毕") {
                                                        return data.percent + "%";
                                                    }
                                                    return "";
                                                }
                                            },
                                            emphasis: {
                                                show: true,
                                                textStyle: {
                                                    fontSize: '15',
                                                    fontWeight: 'bold'
                                                }
                                            }
                                        },
                                        labelLine: {
                                            normal: {
                                                show: false
                                            }
                                        },
                                        data: [
                                            {value: result.data.traineeCountFinishShooting, name: '打靶完毕'},
                                            {value: result.data.traineeCountNotShooting, name: '未打靶'}
                                        ]
                                    }
                                ]
                            };
                            myChart.setOption(option);
                            <!--结束显示echart-->
                        } else {
                            showMessage("失败", result.msg, false);
                        }
                    }
                });

            }else {

            }
        }else{//显示未全部完成射击而更迭射击人员
            if(confirm('有靶位非' +
                '“打靶完毕”状态，确定要更迭射击人员吗')==true){
                $.ajax({
                    url: "/sys/shootingrange/changeShootingTrainee.json",
                    contentType: "application/json",
                    success: function (result) {
                        if (result.ret) {
                            var rendered = Mustache.render(shootingRangeDataTemplate, {
                                traineeShooting_deviceGroup_data_list: result.data.traineeShooting_deviceGroup_data,
                                nextGroupTrainee: result.data.nextGroupTraineeNames,
                                currentTrainneeGroup: result.data.currentTrainneeGroupIndex,
                                sumOfTraineeGroup: result.data.sumOfTraineeGroupCount,
                                sumOfTrainee: result.data.sumOfTraineeCount,
                                traineeCountFinish: result.data.traineeCountFinishShooting,
                                traineeCountNotFinish: result.data.traineeCountNotShooting,
                                traineeCountAbsent: result.data.traineeCountAbsentShooting,
                                "showPhoto": function () {
                                    if (this.photo == null) {
                                        return "/assets/images/icon-person.png";
                                    }
                                    else {
                                        return this.photo;
                                    }
                                },
                                "showTraineeStatus": function () {
                                    if (this.name == null) {
                                        return null;
                                    }
                                    else {
                                        return this.traineeStatus == 0 ? '：等候打靶' : (this.traineeStatus == 1 ? '：正常登陆' : (this.traineeStatus == 2 ? '：未登陆' : (this.traineeStatus == 3 ? '：打靶中' : (this.traineeStatus == 4 ? '：打靶完毕' : "：缺席"))));
                                    }
                                },
                                "showDisplayStatus": function () {
                                    return this.displayStatus == 0 ? '未知' : (this.displayStatus == 1 ? '正常' : "异常");

                                },
                                "showCameraStatus": function () {
                                    return this.cameraStatus == 0 ? '未知' : (this.cameraStatus == 1 ? '正常' : "异常");
                                },
                                "showTargetStatus": function () {
                                    return this.targetStatus == 0 ? '未知' : (this.targetStatus == 1 ? '正常' : "异常");
                                }


                            });
                            $("#container").html(rendered);
                            setLeftWidth();//动态设置界面宽度，bamian
                            setTableWidth();
                            showScores();

                            function showScores() {
                                var diameter = 150;//._img-parent为靶图的父元素为正方形宽高度为150，靶图无论大小都被限制为150px见方大小，
                                var bullsEyeRadius = 150 / 10;//靶心半径为靶图直径的１０分之一
                                //var  targetBullsEyeRadius=this.radius;
                                //var radio=FloatDiv(bullsEyeRadius,targetBullsEyeRadius);
                                var bullsEye_X = FloatDiv(diameter, 2)//
                                var bullsEye_Y = FloatMul(diameter, 0.6)//
                                var deviceGroup_data_list = result.data.traineeShooting_deviceGroup_data//等同traineeShooting_deviceGroup_data_list
                                <!--开始把数组中回传数据的px,py即x,y坐标根据两端坐标比例进行变换,在存回去,供下一步解析模板使用-->
                                for (var m = 0; m < deviceGroup_data_list.length; m++) {
                                    var deviceGroupDataList = deviceGroup_data_list[m];
                                    var shootingScoreList = deviceGroupDataList.shootingScoreList;
                                    if (shootingScoreList != null) {
                                        for (var n = 0; n < shootingScoreList.length; n++) {

                                            var score = deviceGroupDataList.shootingScoreList[n];
                                            var radius = score.radius;//传回来的显靶终端靶心半径以px为单位
                                            // var radius_mm=score.mmOfRadius;////传回来的显靶终端靶心半径以mm为单位,，暂时没有用因为服务器端用的的以px为单位
                                            var px = score.px;//取得回传的x
                                            var py = score.py;//取得回传的y
                                            var radio = FloatDiv(bullsEyeRadius, radius);
                                            var X = bullsEye_X + px * radio;//返回的x以左为负数
                                            var Y = bullsEye_Y + py * radio;//返回的y以下为正
                                            deviceGroup_data_list[m].shootingScoreList[n].px = X-1.5;
                                            deviceGroup_data_list[m].shootingScoreList[n].py = Y-1.5;
                                        }
                                    }
                                }
                                <!--结束把数组中回传数据的px,py即x,y坐标根据两端坐标比例进行变换,在存回去,供下一步解析模板使用-->

                                for (var i = 0; i < deviceGroup_data_list.length; i++) {
                                    var rendered = Mustache.render(scoreTemplate, {scoreList: deviceGroup_data_list[i].shootingScoreList});
                                    $("#_goals" + deviceGroup_data_list[i].deviceGroupIndex).append(rendered);//注意这里是append,不是html(html是覆盖,append是添加)
                                }
                            }

                            <!--开始显示时间栏右侧的时间日期和星期几-->
                            setInterval(updateTime, 1000);
                            updateTime();

                            function updateTime() {
                                var cd = new Date();
                                var myweek = ['星期天', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
                                var mytime = zeroPadding(cd.getHours(), 2) + ':' + zeroPadding(cd.getMinutes(), 2) + ':' + zeroPadding(cd.getSeconds(), 2);
                                var mydate = zeroPadding(cd.getFullYear(), 4) + '-' + zeroPadding(cd.getMonth() + 1, 2) + '-' + zeroPadding(cd.getDate(), 2);
                                var myday = cd.getDay();
                                var myweekday = myweek[myday];
                                $("#mytime").html(mytime);
                                $("#mydate").html(mydate);
                                $("#myweekday").html(myweekday);
                            };
                            <!--结束显示时间栏右侧的时间日期和星期几-->
                            <!--开始显示时钟-->
                            var hourDom = document.getElementById('hour');
                            var minDom = document.getElementById('min');
                            var secDom = document.getElementById('sec');
                            var cricle = document.getElementById('mycircle');
                            //创建表盘，ul宽高为wrap宽高，以wrap中心点为变换基点，动态分配6°的li
                            for (var i = 0; i < 60; i++) {
                                var li = document.createElement('li');
                                cricle.appendChild(li);
                                if (i % 5 == 0) {
                                    li.style.height = "6px";
                                    li.style.width = "1px";
                                }
                                li.style.transform = 'rotate(' + i * 6 + 'deg)';
                            }

                            setInterval(function () {
                                var date = new Date();
                                var hour = date.getHours();
                                var min = date.getMinutes();
                                var sec = date.getSeconds();
                                min += sec / 60;
                                hour += min / 60;

                                //当前时间*每个单位时间走的角度=指针指向
                                hourDom.style.transform = 'rotate(' + hour * 30 + 'deg)';
                                minDom.style.transform = 'rotate(' + min * 6 + 'deg)';
                                secDom.style.transform = 'rotate(' + sec * 6 + 'deg)';
                            }, 1000);
                            <!--结束显示时钟-->

                            <!--开始显示echart-->
                            var dom = document.getElementById("rate");
                            var myChart = echarts.init(dom);
                            option = {
                                tooltip: {
                                    trigger: 'item',
                                    formatter: '{a} <br/>{b}: {c} ({d}%)'
                                },
                                itemStyle: {
                                    normal: {
                                        //自定义颜色
                                        color: function (params) {
                                            //自定义颜色
                                            var colorList = [
                                                '#41C7DB', '#DDDDDD'
                                            ];
                                            return colorList[params.dataIndex];
                                        }
                                    }
                                },
                                series: [
                                    {
                                        name: '访问来源',
                                        type: 'pie',
                                        radius: ['80%', '100%'],
                                        avoidLabelOverlap: false,
                                        label: {
                                            normal: {
                                                show: true,
                                                position: 'center',
                                                formatter: function (data) { // 设置圆饼图中间文字排版
                                                    if (data.name == "打靶完毕") {
                                                        return data.percent + "%";
                                                    }
                                                    return "";
                                                }
                                            },
                                            emphasis: {
                                                show: true,
                                                textStyle: {
                                                    fontSize: '15',
                                                    fontWeight: 'bold'
                                                }
                                            }
                                        },
                                        labelLine: {
                                            normal: {
                                                show: false
                                            }
                                        },
                                        data: [
                                            {value: result.data.traineeCountFinishShooting, name: '打靶完毕'},
                                            {value: result.data.traineeCountNotShooting, name: '未打靶'}
                                        ]
                                    }
                                ]
                            };
                            myChart.setOption(option);
                            <!--结束显示echart-->
                        } else {
                            showMessage("失败", result.msg, false);
                        }
                    }
                });
            }else {

            }

        }
    }
    //开始射击
    function startShooting(){

        var allLogin=true;
        //在更迭射击人员前要判断一下是否还有未完成射击的人员，如有则须确定是否继续
        $("._info-list").each(function(){
            var info = $(this).find("span").text();
            if(info.indexOf("未登陆")!=-1){
                allLogin=false;
            }
        });
        if(allLogin) {
            if(confirm('所有靶位显示已登陆，确定要开始射击吗')==true){
                $.ajax({
                    url: "/sys/shootingrange/startShooting.json",
                    contentType: "application/json",
                    success: function (result) {
                        if (result.ret) {
                        }
                    }
                });
            }else{
            }
        }else{
            if(confirm('有靶位显示未登陆，确定要开始射击吗')==true){
                $.ajax({
                    url: "/sys/shootingrange/startShooting.json",
                    contentType: "application/json",
                    success: function (result) {
                        if (result.ret) {
                        }
                    }
                });

            }else{

            }
        }
    }


    //射击
    function endShooting(){
        var shootingRangeDataTemplate = $('#shootingRangeDataTemplate').html();
        Mustache.parse(shootingRangeDataTemplate);
        var scoreTemplate = $('#scoreTemplate').html();
        Mustache.parse(scoreTemplate);
        var finishShooting=true;
        //在更迭射击人员前要判断一下是否还有未完成射击的人员，如有则须确定是否继续
        $("._info-list").each(function(){
            var info = $(this).find("span").text();
            if(info.indexOf("打靶完毕")==-1){
                finishShooting=false;
            }
        });
        //所有人员已经打靶结束不需要任何操作
        if(finishShooting){}
        else{
            if(confirm('有靶位非' +
                '“打靶完毕”状态，确定要借宿打靶吗')==true){
                $.ajax({
                    url: "/sys/shootingrange/endShootingTrainee.json",
                    contentType: "application/json",
                    success: function (result) {
                        if (result.ret) {

                        } else {

                        }
                    }
                });
            }else {

            }
        }

    }


    function sendTestData(){
        $.ajax({
            url: "/rabbitmq/senddata",
            success: function (result) {
                if (result.ret) {
                }
            }
        });
    }
</script>
<script type="text/javascript">
    var websocket = null;
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8080/websocket/socketServer");
    }
    else if ('MozWebSocket' in window) {
        websocket = new MozWebSocket("ws://localhost:8080/websocket/socketServer");
    }
    else {
        websocket = new SockJS("http://localhost:8080/sockjs/socketServer");
    }
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onerror = onError;
    websocket.onclose = onClose;
    function onOpen(openEvt) {
        alert("连接成功"+openEvt.Data);
    }
    function onMessage(evt) {

        var json = JSON.parse(evt.data);
        var scoreTemplate = $('#scoreTemplate').html();
        var scoreTableTemplate=$('#scoreTableTemplate').html();
        Mustache.parse(scoreTemplate);
        Mustache.parse(scoreTableTemplate);
        var code=json.code;
        switch (code) {
            case 0://信息来自traineeController 的ｌｏｇｉｎ和app端点击打靶完毕后显示的打靶完毕，刚切换到位则为未登陆，点击了打靶则为打靶中，一般不会出现“等候中，和缺席”
                var deviceGroupIndex=json.data.targetIndex;//其实应该是deviceGroupIndex
                var traineeStatus=json.data.traineeStatus;
                switch (traineeStatus) {
                    case 0:
                        document.getElementById("trainee_"+deviceGroupIndex).innerText = "：等候中";
                        break;
                    case 1:
                        document.getElementById("trainee_"+deviceGroupIndex).innerText = "：正常登陆";
                        break;
                    case 2:
                        document.getElementById("trainee_"+deviceGroupIndex).innerText = "：未登陆";
                        break;
                    case 3:
                        document.getElementById("trainee_"+deviceGroupIndex).innerText = "：打靶中";
                        break;
                    case 4:
                        document.getElementById("trainee_"+deviceGroupIndex).innerText = "：打靶完毕";
                        break;
                    case 5:
                        document.getElementById("trainee_"+deviceGroupIndex).innerText = "：缺席";
                        break;

                }
                break;
            case 1:
                var radius=json.radius;//传回来的显靶终端靶心半径以px为单位
                var mm_radius=json.mmOfRadius;//传回来的显靶终端靶心半径以mm为单位,暂时不用
                var deviceGroupIndex=json.deviceGroupIndex;
                var scoreList=json.holes;
                var diameter=150;//._img-parent为靶图的父元素为正方形宽高度为150，靶图无论大小都被限制为150px见方大小，
                var bullsEyeRadius=150/10;//靶心半径为靶图直径的１０分之一
                var bullsEye_X=FloatDiv(diameter,2)//靶心ｘ坐标
                var bullsEye_Y=FloatMul(diameter,0.6)//
                for (var n = 0; n < scoreList.length; n++) {
                    var score = scoreList[n];
                    // var radius_mm=score.mmOfRadius;////传回来的显靶终端靶心半径以mm为单位,，暂时没有用因为服务器端用的的以px为单位
                    var px = score.px;//取得回传的x
                    var py = score.py;//取得回传的y
                    var radio = FloatDiv(bullsEyeRadius, radius);
                    var X = bullsEye_X + px * radio;//返回的x以右为正
                    var Y = bullsEye_Y + py * radio;//返回的y以下为正
                    scoreList[n].px = X-1.5;//显示靶孔的半径为3，定位的时候是以小孔的左上角坐标定的，所以要在
                    // 变换完的坐标（这时，以左上角为坐标原点，向右，向下为正），减去小孔半径
                    scoreList[n].py = Y-1.5;
                }

                var renderedScore = Mustache.render(scoreTemplate, {scoreList:json.holes});
                var renderedTableScore = Mustache.render(scoreTableTemplate, {scoreList:json.holes,totalScore:json.totalScore});
                $("#_scoretableparent" + deviceGroupIndex).find('table').remove();
                $("#_scoretableparent" + deviceGroupIndex).append(renderedTableScore);//
                setTableWidth();
                $("#_goals" + deviceGroupIndex).find('div').remove();//去掉以前显示的靶环,
                $("#_goals" + deviceGroupIndex).append(renderedScore);//
                break;
            /*          {
                            "code": 1,
                            "data": {
                            "targetIndex":1
                            "deviceType": "1",
                            "deviceStatus": 50
                       　　},
                        　}*/
            case 2://设备状态，信息包括三部都为2，：deviceType=0:靶机；1：采集；2：显靶，deviceStatus：具体判断显靶只需要０，１正常，异常状态，靶机可能会多一点：上纸张异常，退纸张异常，或其他
                var deviceType=json.data.deviceType;
                var deviceStatus=json.data.deviceStatus;
                var deviceGroupIndex=json.data.targetIndex;
                switch (deviceType) {
                    case 0://靶机，状态数目未定设备状态

                        switch (deviceStatus) {
                            case 0://正常
                                break;
                            case 1://上纸异常
                                break;
                            case 2:
                                break;

                        }
                    case 1://采集
                        var deviceStatus=json.data.deviceStatus;
                        switch (deviceStatus) {
                            case 0:
                                break;
                            case 1:
                                break;

                        }
                        break;
                    case 2://显靶
                        var deviceStatus=json.data.deviceStatus;
                        switch (deviceStatus) {
                            case 0:
                                document.getElementById("display_"+deviceGroupIndex).innerText = "显靶：正常";
                                break;
                            case 1:
                                document.getElementById("display_"+deviceGroupIndex).innerText = "显靶：异常";
                                break;

                        }
                        break;
                }
                break;
            default:

        }
    }
    function onOpen() {
    }
    function onError() {}
    function onClose() {}

    /*    function doSendUser() {
            alert(websocket.readyState + ":" + websocket.OPEN);
            if (websocket.readyState == websocket.OPEN) {
                var msg = document.getElementById("inputMsg").value;
                websocket.send(msg);//调用后台handleTextMessage方法
                alert("发送成功!");
            } else {
                alert("连接失败!");
            }
        }*/
    /*    function doSendUsers() {
            if (websocket.readyState == websocket.OPEN) {
                var msg = document.getElementById("inputMsg").value;
                websocket.send("#everyone#"+msg);//调用后台handleTextMessage方法
                alert("发送成功!");
            } else {
                alert("连接失败!");
            }
        }*/
    window.close=function()
    {
        websocket.onclose();
    }
    function websocketClose() {
        websocket.close();
    }

    function deviceDetection(){
        $.ajax({
            url: "/sys/device/devicedetection.json",
            contentType: "application/json",
            success: function (result) {
                if (result.ret) {
                }
            }
        });
    }

</script>

<%--<script>
    var data = '{"name": "mkyong","age": 30,"address": {"streetAddress": "88 8nd Street","city": "New York"},"phoneNumber": [{"type": "home","number": "111 111-1111"},{"type": "fax","number": "222 222-2222"}]}';

    var json = JSON.parse(data);

    alert(json["name"]); //mkyong
    alert(json.name); //mkyong

    alert(json.address.streetAddress); //88 8nd Street
    alert(json["address"].city); //New York

    alert(json.phoneNumber[0].number); //111 111-1111
    alert(json.phoneNumber[1].type); //fax

    alert(json.phoneNumber.number); //undefined
</script>--%>
</body>
</html>

package com.mmall.controller;

import com.mmall.rabbitmq.MessageProducer;
import entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/rabbitmq")
public class RabbitmqController {
    @Resource
    private MessageProducer messageProducer;
    @RequestMapping(value="/initcamera",method = RequestMethod.GET)
    @ResponseBody
    public void rabbitmqSendtoCamera(HttpServletRequest request, HttpServletResponse response,String json){

        //messageProducer.sendDirectMessage("camera-to-server-exchange","camera-to-server-markdata",message);
        messageProducer.sendTopicMessage("server-to-camera-exchange","server-to-camera-command-routing-key-1",json);

    }

    @RequestMapping(value="/initdisplay",method = RequestMethod.GET)
    @ResponseBody
    public void initDisplay(HttpServletRequest request, HttpServletResponse response,String json){

        messageProducer.sendTopicMessage("server-to-display-exchange","server-to-display-command-routing-key-200",json);

    }

    @RequestMapping(value="/signinbypass",method = RequestMethod.GET)
    @ResponseBody
    public void rabbitmqSignByPass(HttpServletRequest request, HttpServletResponse response){
        String signByPassCommand="{\"code\":0,\"message\":\"登陆者信息\",\"index\":1,\"data\":{\"userId\":1,\"name\":\"张三\",\"department\":\"某某单位\",\"password\":\"111111\",\"shooting_gun\":\"手枪\",\"photopath\":\"\",\"bullet_count\":10,\"target_number\":\"12\",\"group_number\":\"1\"}}";
        //messageProducer.sendTopicMessage("server-to-other-exchange","server-to-display-command-routing-key-200",signByPassCommand);
        messageProducer.sendTopicMessage("server-to-display-exchange","server-to-display-routing-key-D8:63:75:92:D6:CE",signByPassCommand);

    }

    @RequestMapping(value="/senddata",method = RequestMethod.GET)
    @ResponseBody
    public void rabbitmqSendData(HttpServletRequest request, HttpServletResponse response){
        //String markData="{\"mac\":\"E09467C6B60F\",\"radius\":90.0,\"mmOfRadius\":50.5,\"holes\":[{\"id\":1,\"px\":-160.7,\"py\":-345.4,\"mx\":-90.2,\"my\":-193.8,\"score\":6.8,\"offset\":\"ld\"},{\"id\":2,\"px\":166.6,\"py\":-336.9,\"mx\":93.5,\"my\":-189.0,\"score\":6.8,\"offset\":\"rd\"},{\"id\":3,\"px\":-5.5,\"py\":-305.3,\"mx\":-3.1,\"my\":-171.3,\"score\":7.6,\"offset\":\"d\"},{\"id\":4,\"px\":-182.4,\"py\":-272.2,\"mx\":-102.3,\"my\":-152.7,\"score\":7.4,\"offset\":\"ld\"},{\"id\":5,\"px\":-3.2,\"py\":-219.1,\"mx\":-1.8,\"my\":-122.9,\"score\":8.6,\"offset\":\"d\"},{\"id\":6,\"px\":-201.8,\"py\":-159.3,\"mx\":-113.2,\"my\":-89.4,\"score\":8.1,\"offset\":\"ld\"},{\"id\":7,\"px\":203.1,\"py\":-153.0,\"mx\":113.9,\"my\":-85.8,\"score\":8.2,\"offset\":\"rd\"},{\"id\":8,\"px\":2.2,\"py\":-132.7,\"mx\":1.2,\"my\":-74.5,\"score\":9.5,\"offset\":\"d\"},{\"id\":9,\"px\":-201.6,\"py\":-86.2,\"mx\":-113.1,\"my\":-48.4,\"score\":8.6,\"offset\":\"ld\"},{\"id\":10,\"px\":201.6,\"py\":-83.9,\"mx\":113.1,\"my\":-47.1,\"score\":8.6,\"offset\":\"rd\"},{\"id\":11,\"px\":2.6,\"py\":-44.2,\"mx\":1.5,\"my\":-24.8,\"score\":10.5,\"offset\":\"c\"},{\"id\":12,\"px\":338.5,\"py\":5.9,\"mx\":189.9,\"my\":3.3,\"score\":7.2,\"offset\":\"r\"},{\"id\":13,\"px\":8.8,\"py\":45.8,\"mx\":4.9,\"my\":25.7,\"score\":10.5,\"offset\":\"c\"},{\"id\":14,\"px\":89.2,\"py\":131.8,\"mx\":50.1,\"my\":73.9,\"score\":9.2,\"offset\":\"ru\"},{\"id\":15,\"px\":0.4,\"py\":131.5,\"mx\":0.2,\"my\":73.8,\"score\":9.5,\"offset\":\"u\"},{\"id\":16,\"px\":216.7,\"py\":133.0,\"mx\":121.6,\"my\":74.7,\"score\":8.2,\"offset\":\"ru\"},{\"id\":17,\"px\":310.7,\"py\":134.2,\"mx\":174.4,\"my\":75.3,\"score\":7.2,\"offset\":\"ru\"},{\"id\":18,\"px\":129.1,\"py\":133.6,\"mx\":72.4,\"my\":75.0,\"score\":8.9,\"offset\":\"ru\"},{\"id\":19,\"px\":-91.0,\"py\":136.0,\"mx\":-51.1,\"my\":76.3,\"score\":9.2,\"offset\":\"lu\"},{\"id\":20,\"px\":-6.0,\"py\":226.9,\"mx\":-3.4,\"my\":127.3,\"score\":8.5,\"offset\":\"u\"},{\"id\":21,\"px\":1.7,\"py\":312.5,\"mx\":0.9,\"my\":175.3,\"score\":7.5,\"offset\":\"u\"},{\"id\":22,\"px\":181.0,\"py\":373.6,\"mx\":101.5,\"my\":209.6,\"score\":6.4,\"offset\":\"ru\"},{\"id\":23,\"px\":-3.6,\"py\":492.3,\"mx\":-2.0,\"my\":276.2,\"score\":5.5,\"offset\":\"u\"}]}";
        String markData="{\"mac\": \"E09467C6B60F\",\n" +
                "\"radius\": 39.8, \n" +
                "holes: [\n" +
                "    {\n" +
                "        \"center\": {\n" +
                "            \"px\": -125,\n" +
                "            \"py\": -38,\n" +
                "        },\n" +
                "        \"bbox\": [\n" +
                "            {\n" +
                "                \"px\": -130,\n" +
                "                \"py\": -40,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": -120,\n" +
                "                \"py\": -40,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": -120,\n" +
                "                \"py\": -35,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": -130,\n" +
                "                \"py\": -35,\n" +
                "            }\n" +
                "        ],\n" +
                "    },    \n" +
                "\t{\n" +
                "        \"center\": {\n" +
                "            \"px\": 114,\n" +
                "            \"py\": 58,\n" +
                "        },\n" +
                "        \"bbox\": [\n" +
                "            {\n" +
                "                \"px\": 112,\n" +
                "                \"py\": 56,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 118,\n" +
                "                \"py\": 56,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 118,\n" +
                "                \"py\": 60,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 112,\n" +
                "                \"py\": 60,\n" +
                "            }\n" +
                "        ],\n" +
                "    },    \n" +
                "\t{\n" +
                "        \"center\": {\n" +
                "            \"px\": 104,\n" +
                "            \"py\": 28,\n" +
                "        },\n" +
                "        \"bbox\": [\n" +
                "            {\n" +
                "                \"px\": 100,\n" +
                "                \"py\": 26,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 108,\n" +
                "                \"py\": 26,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 108,\n" +
                "                \"py\": 30,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 100,\n" +
                "                \"py\": 30,\n" +
                "            }\n" +
                "        ],\n" +
                "    },    \n" +
                "\t{\n" +
                "        \"center\": {\n" +
                "            \"px\": 114,\n" +
                "            \"py\": 18,\n" +
                "        },\n" +
                "        \"bbox\": [\n" +
                "            {\n" +
                "                \"px\": 110,\n" +
                "                \"py\": 16,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 116,\n" +
                "                \"py\": 16,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 116,\n" +
                "                \"py\": 20,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 110,\n" +
                "                \"py\": 20,\n" +
                "            }\n" +
                "        ],\n" +
                "    },    \n" +
                "\t{\n" +
                "        \"center\": {\n" +
                "            \"px\": 124,\n" +
                "            \"py\": 98,\n" +
                "        },\n" +
                "        \"bbox\": [\n" +
                "            {\n" +
                "                \"px\": 122,\n" +
                "                \"py\": 96,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 126,\n" +
                "                \"py\": 96,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 126,\n" +
                "                \"py\": 100,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 122,\n" +
                "                \"py\": 100,\n" +
                "            }\n" +
                "        ],\n" +
                "    },    \n" +
                "\t{\n" +
                "        \"center\": {\n" +
                "            \"px\": -114,\n" +
                "            \"py\": 58,\n" +
                "        },\n" +
                "        \"bbox\": [\n" +
                "            {\n" +
                "                \"px\": -116,\n" +
                "                \"py\": 56,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": -112,\n" +
                "                \"py\": 56,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": -112,\n" +
                "                \"py\": 60,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": -116,\n" +
                "                \"py\": 60,\n" +
                "            }\n" +
                "        ],\n" +
                "    },    \n" +
                "\t{\n" +
                "        \"center\": {\n" +
                "            \"px\": -134,\n" +
                "            \"py\": 58,\n" +
                "        },\n" +
                "        \"bbox\": [\n" +
                "            {\n" +
                "                \"px\": -136,\n" +
                "                \"py\": 56,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": -132,\n" +
                "                \"py\": 56,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": -132,\n" +
                "                \"py\": 62,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": -136,\n" +
                "                \"py\": 62,\n" +
                "            }\n" +
                "        ],\n" +
                "    },    \n" +
                "\t{\n" +
                "        \"center\": {\n" +
                "            \"px\": 174,\n" +
                "            \"py\": 58,\n" +
                "        },\n" +
                "        \"bbox\": [\n" +
                "            {\n" +
                "                \"px\": 172,\n" +
                "                \"py\": 53,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 176,\n" +
                "                \"py\": 53,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 176,\n" +
                "                \"py\": 60,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 172,\n" +
                "                \"py\": 60,\n" +
                "            }\n" +
                "        ],\n" +
                "    }\n" +
                "]\n" +
                "}";

        String markData1="{\"mac\": \"E09467C6B60F\",\n" +
                "\"radius\": 39.8, \n" +
                "holes: [\n" +
                "    {\n" +
                "        \"center\": {\n" +
                "            \"px\": -125,\n" +
                "            \"py\": -38,\n" +
                "        },\n" +
                "        \"bbox\": [\n" +
                "            {\n" +
                "                \"px\": -127,\n" +
                "                \"py\": -39,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": -123,\n" +
                "                \"py\": -39,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": -123,\n" +
                "                \"py\": -37,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": -127,\n" +
                "                \"py\": -37,\n" +
                "            }\n" +
                "        ],\n" +
                "    },    \n" +
                "\t{\n" +
                "        \"center\": {\n" +
                "            \"px\": 60,\n" +
                "            \"py\": -50,\n" +
                "        },\n" +
                "        \"bbox\": [\n" +
                "            {\n" +
                "                \"px\": 58,\n" +
                "                \"py\": -51,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 61,\n" +
                "                \"py\": -51,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 61,\n" +
                "                \"py\": -48,\n" +
                "            },\n" +
                "            {\n" +
                "                \"px\": 58,\n" +
                "                \"py\": -48,\n" +
                "            }\n" +
                "        ],\n" +
                "    }\n" +
                "]\n" +
                "}";


        //messageProducer.sendDirectMessage("camera-to-server-exchange","camera-to-server-markdata",message);
        messageProducer.sendDirectMessage("camera-to-server-exchange","camera-to-server-markdata",markData);

    }

    @RequestMapping(value="/senddata1",method = RequestMethod.GET)
    @ResponseBody
    public void rabbitmqSendData1(HttpServletRequest request, HttpServletResponse response){
        String markData="{\"mac\":\"E09467C6B60F\",\"radius\":90.0,\"mmOfRadius\":50.5,\"holes\":[{\"id\":1,\"px\":-160.7,\"py\":-345.4,\"mx\":-90.2,\"my\":-193.8,\"score\":6.8,\"offset\":\"ld\"},{\"id\":2,\"px\":166.6,\"py\":-336.9,\"mx\":93.5,\"my\":-189.0,\"score\":6.8,\"offset\":\"rd\"},{\"id\":3,\"px\":-5.5,\"py\":-305.3,\"mx\":-3.1,\"my\":-171.3,\"score\":7.6,\"offset\":\"d\"},{\"id\":4,\"px\":-182.4,\"py\":-272.2,\"mx\":-102.3,\"my\":-152.7,\"score\":7.4,\"offset\":\"ld\"},{\"id\":5,\"px\":-3.2,\"py\":-219.1,\"mx\":-1.8,\"my\":-122.9,\"score\":8.6,\"offset\":\"d\"},{\"id\":6,\"px\":-201.8,\"py\":-159.3,\"mx\":-113.2,\"my\":-89.4,\"score\":8.1,\"offset\":\"ld\"},{\"id\":7,\"px\":203.1,\"py\":-153.0,\"mx\":113.9,\"my\":-85.8,\"score\":8.2,\"offset\":\"rd\"},{\"id\":8,\"px\":2.2,\"py\":-132.7,\"mx\":1.2,\"my\":-74.5,\"score\":9.5,\"offset\":\"d\"},{\"id\":9,\"px\":-201.6,\"py\":-86.2,\"mx\":-113.1,\"my\":-48.4,\"score\":8.6,\"offset\":\"ld\"},{\"id\":10,\"px\":201.6,\"py\":-83.9,\"mx\":113.1,\"my\":-47.1,\"score\":8.6,\"offset\":\"rd\"},{\"id\":11,\"px\":2.6,\"py\":-44.2,\"mx\":1.5,\"my\":-24.8,\"score\":10.5,\"offset\":\"c\"},{\"id\":12,\"px\":338.5,\"py\":5.9,\"mx\":189.9,\"my\":3.3,\"score\":7.2,\"offset\":\"r\"},{\"id\":13,\"px\":8.8,\"py\":45.8,\"mx\":4.9,\"my\":25.7,\"score\":10.5,\"offset\":\"c\"},{\"id\":14,\"px\":89.2,\"py\":131.8,\"mx\":50.1,\"my\":73.9,\"score\":9.2,\"offset\":\"ru\"},{\"id\":15,\"px\":0.4,\"py\":131.5,\"mx\":0.2,\"my\":73.8,\"score\":9.5,\"offset\":\"u\"},{\"id\":16,\"px\":216.7,\"py\":133.0,\"mx\":121.6,\"my\":74.7,\"score\":8.2,\"offset\":\"ru\"},{\"id\":17,\"px\":310.7,\"py\":134.2,\"mx\":174.4,\"my\":75.3,\"score\":7.2,\"offset\":\"ru\"},{\"id\":18,\"px\":129.1,\"py\":133.6,\"mx\":72.4,\"my\":75.0,\"score\":8.9,\"offset\":\"ru\"},{\"id\":19,\"px\":-91.0,\"py\":136.0,\"mx\":-51.1,\"my\":76.3,\"score\":9.2,\"offset\":\"lu\"},{\"id\":20,\"px\":-6.0,\"py\":226.9,\"mx\":-3.4,\"my\":127.3,\"score\":8.5,\"offset\":\"u\"},{\"id\":21,\"px\":1.7,\"py\":312.5,\"mx\":0.9,\"my\":175.3,\"score\":7.5,\"offset\":\"u\"},{\"id\":22,\"px\":181.0,\"py\":373.6,\"mx\":101.5,\"my\":209.6,\"score\":6.4,\"offset\":\"ru\"},{\"id\":23,\"px\":-3.6,\"py\":492.3,\"mx\":-2.0,\"my\":276.2,\"score\":5.5,\"offset\":\"u\"}]}";


        //messageProducer.sendDirectMessage("camera-to-server-exchange","camera-to-server-markdata",message);
        messageProducer.sendDirectMessage("camera-to-server-exchange","camera-to-server-markdata",markData);

    }
}
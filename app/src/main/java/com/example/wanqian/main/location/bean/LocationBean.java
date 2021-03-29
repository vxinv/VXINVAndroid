package com.example.wanqian.main.location.bean;

import java.io.Serializable;
import java.util.List;

public class LocationBean implements Serializable {

    /**
     * code : 200
     * data : [[{"devId":"WQDSN0AA000A0024","devStatus":"0","deviceId":"6c64e3e8-08e3-495b-839b-b974d98fbff8","eventTime":"20191113T220009Z","groupId":2,"latitude":"39.996214","longitude":"116.38624","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"30"},{"devId":"WQDSN0AA000A0035","devStatus":"0","deviceId":"185f4b4f-0120-4264-a004-41a994511986","eventTime":"20191113T203650Z","groupId":2,"latitude":"39.996214","longitude":"116.38634","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"169"}],[{"devId":"WQDSN3AA000A0013","devStatus":"0","deviceId":"5f97f214-c88c-4648-85d9-9d05d0cd0666","eventTime":"20191113T210300Z","groupId":3,"latitude":"39.996172","longitude":"116.386099","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"105"},{"devId":"WQDSN0AA000A0020","devStatus":"0","deviceId":"0b1a84af-9509-42c7-a741-7caf03a6d29a","eventTime":"20191113T202359Z","groupId":3,"latitude":"39.996172","longitude":"116.386199","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"367"}],[{"devId":"WQDSN3AA000A0006","devStatus":"0","deviceId":"18008112-55b3-48aa-a939-d5fe51f7ff1d","eventTime":"20191113T210513Z","groupId":4,"latitude":"39.996101","longitude":"116.385975","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"286"},{"devId":"WQDSN3AA000A0014","devStatus":"0","deviceId":"81cd6c7c-30fb-4ee8-992a-3feae2bf86e4","eventTime":"20191113T204506Z","groupId":4,"latitude":"39.996101","longitude":"116.386075","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"259"}],[{"devId":"WQDSN3AA000A0008","devStatus":"0","deviceId":"936a3e04-6900-458d-bd0b-91cc4f557899","eventTime":"20191113T204313Z","groupId":5,"latitude":"39.996094","longitude":"116.385756","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"400"},{"devId":"WQDSN3AA000A0015","devStatus":"0","deviceId":"93bec45b-31fc-43da-873f-dda0561d6e9d","eventTime":"20191113T204332Z","groupId":5,"latitude":"39.996094","longitude":"116.385856","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"394"}],[{"devId":"WQDSN3AA000A0016","devStatus":"0","deviceId":"5322d1ce-6484-44ad-95f9-bd5f289c9c0c","eventTime":"20191031T042844Z","groupId":6,"latitude":"39.996321","longitude":"116.385793","onlineStatus":"3","reportType":"1","rubbishHeight":"520","rubbishL":"499"},{"devId":"WQDSN0AA000A0076","devStatus":"0","deviceId":"9dfa60f4-5dba-4496-999a-52e51b213a0e","eventTime":"20191113T203253Z","groupId":6,"latitude":"39.996321","longitude":"116.385893","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"207"}],[{"devId":"WQDSN0AA000A0019","devStatus":"0","deviceId":"7d449714-c777-46f1-b5b1-66862e442ce1","eventTime":"20191113T205715Z","groupId":7,"latitude":"39.995936","longitude":"116.385757","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"415"},{"devId":"WQDSN0AA000A0078","devStatus":"0","deviceId":"ab74fbe0-ec1e-4ab0-b2b4-6325ca0d4ed1","eventTime":"20191113T205657Z","groupId":7,"latitude":"39.995936","longitude":"116.385857","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"362"}],[{"devId":"WQDSN0AA000A0033","devStatus":"0","deviceId":"55bd030b-53ad-4114-a4cb-46b65aa691c4","eventTime":"20191113T204920Z","groupId":8,"latitude":"39.995713","longitude":"116.385795","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"436"},{"devId":"WQDSN0AA000A0069","devStatus":"0","deviceId":"b08dc544-7480-4d92-8c77-4c88469492ec","eventTime":"20191113T204358Z","groupId":8,"latitude":"39.995713","longitude":"116.385895","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"443"}],[{"devId":"WQDSN3AA000A0002","devStatus":"0","deviceId":"fe793a9e-8063-465e-b7be-645049e6b28f","eventTime":"20191113T202845Z","groupId":9,"latitude":"39.995744","longitude":"116.386148","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"464"},{"devId":"WQDSN0AA000A0023","devStatus":"0","deviceId":"218cf284-9675-4537-a2d1-d23f0ee331db","eventTime":"20191113T210555Z","groupId":9,"latitude":"39.995744","longitude":"116.386048","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"404"}],[{"devId":"WQDSN0AA000A9997","devStatus":"0","deviceId":"2b72f364-304a-40e4-8d16-4d5ce75696b3","eventTime":"20191101T065121Z","groupId":-267,"latitude":"40.452745","longitude":"115.95949","onlineStatus":"3","reportType":"1,8,64","rubbishHeight":"1000","rubbishL":"750"}],[{"devId":"WQDSN0AA000A0025","devStatus":"0","deviceId":"d75c6691-7c93-47fe-b23a-500af067073e","eventTime":"20191113T204703Z","groupId":10,"latitude":"39.99575","longitude":"116.386559","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"284"},{"devId":"WQDSN0AA000A0031","devStatus":"0","deviceId":"fee696f4-6a35-4e51-baba-d144a2d91842","eventTime":"20191113T210235Z","groupId":10,"latitude":"39.99575","longitude":"116.386459","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"176"}],[{"devId":"WQDSN0AA000A9998","devStatus":"0","deviceId":"5d519960-84d2-48bd-9ecf-3252d06e78ac","eventTime":"20190822T005845Z","groupId":-268,"latitude":"40.451894","longitude":"115.941605","onlineStatus":"3","reportType":"1,8,64","rubbishHeight":"1000","rubbishL":"350"}],[{"devId":"WQDSN3AA000A0012","devStatus":"0","deviceId":"15bfb4fd-6be4-4a9f-ba6c-899e04ab3946","eventTime":"20191113T203041Z","groupId":11,"latitude":"39.995423","longitude":"116.386573","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"402"},{"devId":"WQDSN0AA000A0081","devStatus":"0","deviceId":"b1a70a50-cefc-481e-9deb-ad11a36f78a1","eventTime":"20191113T205907Z","groupId":11,"latitude":"39.995423","longitude":"116.386673","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"86"}],[{"devId":"WQDSN0AA000A0050","devStatus":"0","deviceId":"09c1e973-8091-46e0-8f6d-94e9c88d3aed","eventTime":"20191113T205124Z","groupId":12,"latitude":"39.995396","longitude":"116.386184","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"357"},{"devId":"WQDSN0AA000A0088","devStatus":"0","deviceId":"5806b287-2ae8-4ada-b7ac-08f0a7f93185","eventTime":"20191113T205503Z","groupId":12,"latitude":"39.995396","longitude":"116.386084","onlineStatus":"1","reportType":"2","rubbishHeight":"520","rubbishL":"250"}],[{"devId":"WQDSN3AA000A0011","devStatus":"0","deviceId":"49aec3f5-0b88-407c-85f1-a276d791de65","eventTime":"20191113T204646Z","groupId":13,"latitude":"39.995415","longitude":"116.385901","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"413"},{"devId":"WQDSN0AA000A0093","devStatus":"0","deviceId":"807e9472-2078-49bd-8f6a-866f4817fe5e","eventTime":"20191113T204110Z","groupId":13,"latitude":"39.995415","longitude":"116.385801","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"395"}],[{"devId":"WQDSN0AA000A1832","devStatus":"0","deviceId":"f905aeb4-dbb4-466c-b763-7b78d411a1d0","eventTime":"20191113T215318Z","groupId":-271,"latitude":"39.988007","longitude":"116.289457","onlineStatus":"1","reportType":"64","rubbishHeight":"600","rubbishL":"13"}],[{"devId":"WQDSN0AA000A0056","devStatus":"0","deviceId":"832ae72e-08b5-469a-9e2a-a477e2490eae","eventTime":"20191113T205206Z","groupId":14,"latitude":"39.994635","longitude":"116.385896","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"378"},{"devId":"WQDSN0AA000A0075","devStatus":"0","deviceId":"41ab0a62-eb7d-4b6e-a288-08f0e1a9c799","eventTime":"20191113T204656Z","groupId":14,"latitude":"39.994635","longitude":"116.385996","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"380"}],[{"devId":"WQDSN0AA000A1849","devStatus":"0","deviceId":"30a070dd-4651-400f-8274-d125f98476e0","eventTime":"20191113T215526Z","groupId":-272,"latitude":"39.988007","longitude":"116.289357","onlineStatus":"1","reportType":"64","rubbishHeight":"600","rubbishL":"13"}],[{"devId":"WQDSN3AA000A0005","devStatus":"0","deviceId":"ac2d71e3-0cb5-434a-b07a-b34fe819652b","eventTime":"20191113T210352Z","groupId":15,"latitude":"39.994647","longitude":"116.386349","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"451"},{"devId":"WQDSN0AA000A0096","devStatus":"0","deviceId":"2ef01a57-e33a-46fe-9faa-d665fd932d27","eventTime":"20191113T203305Z","groupId":15,"latitude":"39.994647","longitude":"116.386249","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"438"}],[{"devId":"WQDSN3AA000A0010","devStatus":"0","deviceId":"dbd6163b-2717-4340-871a-8483702a1416","eventTime":"20191113T205358Z","groupId":16,"latitude":"39.994652","longitude":"116.386576","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"269"},{"devId":"WQDSN0AA000A0027","devStatus":"0","deviceId":"e44bdc7d-7b25-496f-9e50-0192bbc65c16","eventTime":"20191113T203420Z","groupId":16,"latitude":"39.994652","longitude":"116.386676","onlineStatus":"1","reportType":"1","rubbishHeight":"520","rubbishL":"430"}],[{"devId":"WQDSN3AA000A0003","devStatus":"0","deviceId":"3bd6bb89-1e6a-4c7e-9c5f-d49abd6b9c57","eventTime":"20191113T203315Z","groupId":17,"latitude":"39.99445","longitude":"116.386735","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"369"},{"devId":"WQDSN0AA000A0061","devStatus":"0","deviceId":"e66b27a0-4de7-4894-a278-849702a788a4","eventTime":"20191113T230102Z","groupId":17,"latitude":"39.99445","longitude":"116.386635","onlineStatus":"1","reportType":"2","rubbishHeight":"520","rubbishL":"222"}],[{"devId":"WQDSN3AA000A0004","devStatus":"0","deviceId":"d41d790f-07c3-4d20-92d1-1800491dc0a4","eventTime":"20191113T210941Z","groupId":18,"latitude":"39.99434","longitude":"116.386669","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"497"},{"devId":"WQDSN0AA000A0072","devStatus":"0","deviceId":"c9ddc0b9-4806-4ba5-9dc4-c706be04b194","eventTime":"20191113T205944Z","groupId":18,"latitude":"39.99434","longitude":"116.386769","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"410"}],[{"devId":"WQDSN3AA000A0001","devStatus":"0","deviceId":"910bd975-7b05-433d-b3be-3aab7656ae88","eventTime":"20191113T205002Z","groupId":19,"latitude":"39.994168","longitude":"116.386701","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"442"},{"devId":"WQDSN0AA000A0073","devStatus":"0","deviceId":"f2a86dbb-4a21-4bbc-81a2-207a3695f777","eventTime":"20191113T203836Z","groupId":19,"latitude":"39.994168","longitude":"116.386801","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"435"}],[{"devId":"WQDSN0AA000A0022","devStatus":"0","deviceId":"a1c15df9-88d0-48d0-bb32-0a5271da6fd9","eventTime":"20191113T203032Z","groupId":20,"latitude":"39.994095","longitude":"116.386301","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"219"},{"devId":"WQDSN0AA000A0046","devStatus":"0","deviceId":"e6d8bfb6-5062-4e60-b9af-41b41220682d","eventTime":"20191113T204117Z","groupId":20,"latitude":"39.994095","longitude":"116.386201","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"365"}],[{"devId":"WQDSN0AA000A0051","devStatus":"0","deviceId":"5b59e3fb-6b6b-4741-a520-62bcc8d785bf","eventTime":"20191113T221657Z","groupId":21,"latitude":"39.99412","longitude":"116.385904","onlineStatus":"1","reportType":"1","rubbishHeight":"520","rubbishL":"420"},{"devId":"WQDSN0AA000A0053","devStatus":"0","deviceId":"5a633b98-6232-459e-922c-880d961b0fe4","eventTime":"20191113T204758Z","groupId":21,"latitude":"39.99412","longitude":"116.386004","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"510"}],[{"devId":"WQDSN0AA000A0068","devStatus":"0","deviceId":"5671cb26-19fc-419c-b89c-7ccc6d136b52","eventTime":"20191113T205132Z","groupId":22,"latitude":"39.99445","longitude":"116.386234","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"443"},{"devId":"WQDSN0AA000A0091","devStatus":"0","deviceId":"0c500cfa-f2e4-4d8c-866a-63815debd32d","eventTime":"20191113T210134Z","groupId":22,"latitude":"39.99445","longitude":"116.386334","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"432"}],[{"devId":"WQDSN3AA000A0009","devStatus":"0","deviceId":"de817a81-40d3-44c6-ac2a-a11d092c7195","eventTime":"20191113T203542Z","groupId":23,"latitude":"39.994438","longitude":"116.386024","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"438"},{"devId":"WQDSN0AA000A0029","devStatus":"0","deviceId":"7fc30f14-6bcc-4a22-9150-1b7f9207f677","eventTime":"20191113T202916Z","groupId":23,"latitude":"39.994438","longitude":"116.385924","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"371"}],[{"devId":"WQDSN0AA000A0036","devStatus":"0","deviceId":"3c35f472-3d7e-4dd5-8b13-142cb96d2abe","eventTime":"20191113T205250Z","groupId":24,"latitude":"39.993986","longitude":"116.385876","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"424"},{"devId":"WQDSN0AA000A0080","devStatus":"0","deviceId":"27e07073-8585-4f33-b46b-d6742528a867","eventTime":"20191114T002532Z","groupId":24,"latitude":"39.993986","longitude":"116.385976","onlineStatus":"1","reportType":"1","rubbishHeight":"520","rubbishL":"448"}],[{"devId":"WQDSN0AA000A0037","devStatus":"0","deviceId":"43262283-09c4-4030-8e2f-98b9c37f3282","eventTime":"20191113T205118Z","groupId":25,"latitude":"39.993729","longitude":"116.38588","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"485"},{"devId":"WQDSN0AA000A0045","devStatus":"0","deviceId":"5d827fa6-cb15-4c8a-8e81-7e144be028bd","eventTime":"20191113T210227Z","groupId":25,"latitude":"39.993729","longitude":"116.38598","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"402"}],[{"devId":"WQDSN0AA000A0034","devStatus":"0","deviceId":"486fd531-6ca0-4800-81ab-b193cddd0f8f","eventTime":"20191114T002414Z","groupId":26,"latitude":"39.993692","longitude":"116.386129","onlineStatus":"1","reportType":"2","rubbishHeight":"520","rubbishL":"160"},{"devId":"WQDSN0AA000A0049","devStatus":"0","deviceId":"50e42f14-ff73-43ab-96d8-27d4d9ec954a","eventTime":"20191113T204215Z","groupId":26,"latitude":"39.993692","longitude":"116.386229","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"139"}],[{"devId":"WQDSN0AA000A0028","devStatus":"0","deviceId":"96f806d7-c9e1-4211-ab80-023ed16ec9bd","eventTime":"20191114T004010Z","groupId":27,"latitude":"39.993687","longitude":"116.386312","onlineStatus":"1","reportType":"1","rubbishHeight":"520","rubbishL":"419"},{"devId":"WQDSN0AA000A0062","devStatus":"0","deviceId":"25f4b5f1-600c-4d73-b515-32b473eb82aa","eventTime":"20191113T211358Z","groupId":27,"latitude":"39.993687","longitude":"116.386412","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"413"}],[{"devId":"WQDSN0AA000A0017","devStatus":"0","deviceId":"190badb9-6c80-4246-8603-acad6a1a0850","eventTime":"20191113T211531Z","groupId":28,"latitude":"39.993504","longitude":"116.386363","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"485"},{"devId":"WQDSN0AA000A0047","devStatus":"0","deviceId":"108e6399-a9b7-45ac-a4ad-18a5566dace9","eventTime":"20191113T210041Z","groupId":28,"latitude":"39.993504","longitude":"116.386463","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"414"}],[{"devId":"WQDSN0AA000A0054","devStatus":"0","deviceId":"2de5ddd2-0574-46a2-a63a-cb8b14685326","eventTime":"20191113T210851Z","groupId":29,"latitude":"39.993473","longitude":"116.385903","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"484"},{"devId":"WQDSN0AA000A0059","devStatus":"0","deviceId":"9f99a67c-a2fe-4021-aef0-b837ae42f4b7","eventTime":"20191113T205832Z","groupId":29,"latitude":"39.993473","longitude":"116.386003","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"197"}],[{"devId":"WQDSN0AA000A0065","devStatus":"0","deviceId":"6c5719ea-f800-42f5-bd47-60b97e3f4873","eventTime":"20191113T211434Z","groupId":30,"latitude":"39.992882","longitude":"116.386313","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"466"},{"devId":"WQDSN0AA000A0074","devStatus":"0","deviceId":"cee6ec44-bd6c-4d6f-9921-c328179343e4","eventTime":"20191113T211414Z","groupId":30,"latitude":"39.992882","longitude":"116.386213","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"412"}],[{"devId":"WQDSN0AA000A0052","devStatus":"0","deviceId":"43b17b95-e764-4310-9692-4a8f51ebb97a","eventTime":"20191113T205253Z","groupId":31,"latitude":"39.992896","longitude":"116.386584","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"497"},{"devId":"WQDSN0AA000A0077","devStatus":"0","deviceId":"310db817-a084-4f14-bfd6-4dfa16bb100a","eventTime":"20191113T205305Z","groupId":31,"latitude":"39.992896","longitude":"116.386684","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"392"}],[{"devId":"WQDSN0AA000A0018","devStatus":"0","deviceId":"86ac4ccb-b6e8-443c-8865-c7f46543e777","eventTime":"20191113T204429Z","groupId":32,"latitude":"39.992659","longitude":"116.3866","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"384"},{"devId":"WQDSN0AA000A0079","devStatus":"0","deviceId":"a7652eaa-e886-4f6c-99c2-eca046898bba","eventTime":"20191113T205534Z","groupId":32,"latitude":"39.992659","longitude":"116.3867","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"429"}],[{"devId":"WQDSN0AA000A0048","devStatus":"0","deviceId":"0ee904a7-068d-4acc-a247-abc03897e82d","eventTime":"20191113T203159Z","groupId":33,"latitude":"39.992612","longitude":"116.386402","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"432"},{"devId":"WQDSN0AA000A0058","devStatus":"0","deviceId":"f403de1e-f447-490e-a387-093ea5402d54","eventTime":"20191113T205252Z","groupId":33,"latitude":"39.992612","longitude":"116.386502","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"269"}],[{"devId":"WQDSN0AA000A0085","devStatus":"0","deviceId":"2d4b3384-acf0-4b9d-9d51-4908cf1a2ac3","eventTime":"20191113T210841Z","groupId":34,"latitude":"39.992493","longitude":"116.386431","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"393"},{"devId":"WQDSN0AA000A0089","devStatus":"0","deviceId":"c943eef9-2e0b-41ae-acf2-f2948186c149","eventTime":"20191113T205916Z","groupId":34,"latitude":"39.992493","longitude":"116.386531","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"416"}],[{"devId":"WQDSN0AA000A0039","devStatus":"0","deviceId":"2c69535d-5926-43c6-a425-7cf67888f755","eventTime":"20191113T204224Z","groupId":35,"latitude":"39.992539","longitude":"116.386225","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"121"},{"devId":"WQDSN0AA000A0044","devStatus":"0","deviceId":"0ec2e9a6-f088-4eac-bf77-b93d01d0b5ae","eventTime":"20191113T205752Z","groupId":35,"latitude":"39.992539","longitude":"116.386325","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"372"}],[{"devId":"WQDSN0AA000A0082","devStatus":"0","deviceId":"9d92835a-b6e2-45fe-8067-462eb02bed17","eventTime":"20191113T205939Z","groupId":36,"latitude":"39.992651","longitude":"116.386285","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"86"},{"devId":"WQDSN0AA000A0094","devStatus":"0","deviceId":"cfc7ddb4-7733-4f93-ad78-86560ac0781a","eventTime":"20191113T205246Z","groupId":36,"latitude":"39.992651","longitude":"116.386385","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"510"}],[{"devId":"WQDSN0AA000A0026","devStatus":"0","deviceId":"bb364e94-dfb3-4f51-9b70-1d5570ecf045","eventTime":"20191113T210117Z","groupId":37,"latitude":"39.992648","longitude":"116.385904","onlineStatus":"1","reportType":"1","rubbishHeight":"520","rubbishL":"459"},{"devId":"WQDSN0AA000A0064","devStatus":"0","deviceId":"8de296e8-13b7-473f-bfa5-fd0d5256bd35","eventTime":"20191113T204903Z","groupId":37,"latitude":"39.992648","longitude":"116.386004","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"236"}],[{"devId":"WQDSN0AA000A0057","devStatus":"0","deviceId":"8c9e1a70-87e1-437b-a065-6759ead0b46b","eventTime":"20191113T205630Z","groupId":38,"latitude":"39.992517","longitude":"116.385839","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"437"},{"devId":"WQDSN0AA000A0083","devStatus":"0","deviceId":"18e9c450-4760-4ebb-9bdf-708ffc76a5fa","eventTime":"20191113T211218Z","groupId":38,"latitude":"39.992517","longitude":"116.385939","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"412"}],[{"devId":"WQDSN0AA000A0092","devStatus":"0","deviceId":"297d284e-d840-4158-b0c1-373b2bc4b85b","eventTime":"20191113T204308Z","groupId":39,"latitude":"39.992296","longitude":"116.385955","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"438"},{"devId":"WQDSN0AA000A0097","devStatus":"0","deviceId":"f23b96ba-d9cc-4f4c-98fc-31f09c43b7de","eventTime":"20191113T210331Z","groupId":39,"latitude":"39.992296","longitude":"116.386055","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"453"}],[{"devId":"WQDSN0AA000A0040","devStatus":"0","deviceId":"0da65b91-ddc9-49c0-bbf0-8f7749046971","eventTime":"20191113T211313Z","groupId":40,"latitude":"39.992302","longitude":"116.38638","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"273"},{"devId":"WQDSN0AA000A0071","devStatus":"0","deviceId":"d60efd90-ef9d-49ea-a9f7-a7edc3433aae","eventTime":"20191113T222619Z","groupId":40,"latitude":"39.992302","longitude":"116.38648","onlineStatus":"1","reportType":"2","rubbishHeight":"520","rubbishL":"248"}],[{"devId":"WQDSN0AA000A0066","devStatus":"0","deviceId":"6f8b1fd3-ff36-4422-9608-4d1260b2d8b6","eventTime":"20191113T204530Z","groupId":41,"latitude":"39.992317","longitude":"116.386655","onlineStatus":"1","reportType":"2,64","rubbishHeight":"520","rubbishL":"374"},{"devId":"WQDSN0AA000A0086","devStatus":"0","deviceId":"945da580-4440-4e75-aa42-d96195375a06","eventTime":"20191113T211027Z","groupId":41,"latitude":"39.992317","longitude":"116.386755","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"454"}],[{"devId":"WQDSN3AA000A0007","devStatus":"0","deviceId":"5a634db6-30c3-4e2b-8333-39839420d67c","eventTime":"20191113T210036Z","groupId":42,"latitude":"39.996305","longitude":"116.386228","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"260"},{"devId":"WQDSN0AA000A0042","devStatus":"0","deviceId":"0ba01565-c0e9-4920-be08-550de501d437","eventTime":"20191113T205855Z","groupId":42,"latitude":"39.992043","longitude":"116.386723","onlineStatus":"1","reportType":"1,64","rubbishHeight":"520","rubbishL":"442"}]]
     * msg : 有参数成功
     */


    private List<List<DataBean>> data;



    public List<List<DataBean>> getData() {
        return data;
    }

    public void setData(List<List<DataBean>> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * devId : WQDSN0AA000A0024
         * devStatus : 0
         * deviceId : 6c64e3e8-08e3-495b-839b-b974d98fbff8
         * eventTime : 20191113T220009Z
         * groupId : 2
         * latitude : 39.996214
         * longitude : 116.38624
         * onlineStatus : 1
         * reportType : 2,64
         * rubbishHeight : 520
         * rubbishL : 30
         */

        private String devId;
        private String devStatus;
        private String deviceId;
        private String eventTime;
        private int groupId;
        private String latitude;
        private String longitude;
        private String onlineStatus;
        private String reportType;
        private String rubbishHeight;
        private String rubbishL;

        public String getDevId() {
            return devId;
        }

        public void setDevId(String devId) {
            this.devId = devId;
        }

        public String getDevStatus() {
            return devStatus;
        }

        public void setDevStatus(String devStatus) {
            this.devStatus = devStatus;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getEventTime() {
            return eventTime;
        }

        public void setEventTime(String eventTime) {
            this.eventTime = eventTime;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getOnlineStatus() {
            return onlineStatus;
        }

        public void setOnlineStatus(String onlineStatus) {
            this.onlineStatus = onlineStatus;
        }

        public String getReportType() {
            return reportType;
        }

        public void setReportType(String reportType) {
            this.reportType = reportType;
        }

        public String getRubbishHeight() {
            return rubbishHeight;
        }

        public void setRubbishHeight(String rubbishHeight) {
            this.rubbishHeight = rubbishHeight;
        }

        public String getRubbishL() {
            return rubbishL;
        }

        public void setRubbishL(String rubbishL) {
            this.rubbishL = rubbishL;
        }
    }
}

package com.example.myschoolapp.ui.intranet;

import android.content.Context;

import com.example.myschoolapp.R;

import java.util.ArrayList;
import java.util.List;


public class IntranetFunctionUtils {

    public static int index;
    public static Context maincontext;

    public static int state=0;

    public static int [][] icon={{R.mipmap.hall,R.mipmap.benke,R.mipmap.bb,R.mipmap.renli},{R.mipmap.email,R.mipmap.choose,R.mipmap.shiwu,R.mipmap.yanjiusheng},{R.mipmap.liyuan,R.mipmap.yigong,R.mipmap.jingsai,R.mipmap.hosipital},{R.mipmap.library,R.mipmap.internet,R.mipmap.xiaoyuanka,R.mipmap.zhonghang}};

    public static String [][] title={
            {"办事大厅","本科生教务","Blackboard","人力资源"},
            {"学生邮箱","本科生选课","事务中心","研究生会"},
            {"荔园晨风","深大义工","学科竞赛","总医院"},
            {"图书馆","校园网络","校园卡","中行网银"}};

    public static String [][] url={
            {"http://ehall.szu.edu.cn/new/index.html","https://jwb.szu.edu.cn/","https://elearning.szu.edu.cn","https://hr.szu.edu.cn/"},
            {"https://exmail.qq.com/domain/email.szu.edu.cn","http://bkxk.szu.edu.cn/xsxkapp/sys/xsxkapp/*default/index.do","http://swzx.szu.edu.cn/#/","https://grasu.szu.edu.cn/"},
            {"https://bbs.szu.edu.cn/forum.php","https://yigong.szu.edu.cn/","http://jingsai.szu.edu.cn/","https://sugh.szu.edu.cn/"},
            {"http://www.lib.szu.edu.cn/","https://www1.szu.edu.cn/nc/","https://card.szu.edu.cn/","https://www.boc.cn/"}};
    public List<IntranetFunction> getlist (int ind)
    {
        index=ind-1;
        System.out.println("页数："+index);
        List<IntranetFunction> UtilsFunctionList = new ArrayList<>();
        for(int i=0;i<4;i++)
        {
            System.out.print(title[index][i]+" ");
            IntranetFunction intranetFunction=new IntranetFunction();
            intranetFunction.setTitle(title[index][i]);
            intranetFunction.setUrl(url[index][i]);
            intranetFunction.setIcon(icon[index][i]);
            UtilsFunctionList.add(intranetFunction);
        }
        System.out.println();
        return UtilsFunctionList;
    }



    public static int getIndex() {
        return index;
    }

    public static void setIndex(int index) {
        IntranetFunctionUtils.index = index;
    }

    public static Context getMaincontext() {
        return maincontext;
    }

    public static void setMaincontext(Context context) {
        IntranetFunctionUtils.maincontext = context;
    }
}

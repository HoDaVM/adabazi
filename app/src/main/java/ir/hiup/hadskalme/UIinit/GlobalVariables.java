package ir.hiup.hadskalme.UIinit;

/**
 * Created by Mahdi Asiyabi on 9/27/2017.
 */

public class GlobalVariables {
    public static boolean flaginapp, audioplay,fourtype,twotype,threetype;
    public static int typeofmatch=0,numberofusers=0,numberofTeams=0,numberOfuserTeams=0,counter=1,period=1,totalcounter=0,currencounter=1,teamonepoints=0,teamtwopoints=0,teamthreepoints=0,teamfourpoints=0,dor=1,videoRecord=0;
    public static int teamonetime=0,teamtwotime=0,teamthreetime=0,teamfourtime=0,Time=45,UserIsOnline=0,witchDoor=1,POINT=1,teamonefault=0,teamtwofault=0,teamthreefault=0,teamfourfault=0,teamonechangedword=0,teamtwochangedword=0,teamthreechangedword=0,teamfourchangedword=0;
    public static String teamone,teamtwo,teamthree,teamfour,WORD;
    public static int countershomar=0,whichuser=1,wichteam=1,waitinginvites=0,invitecodeok=0;
    public static String[] title = {
            "اشیاء",
            "خوراکی ها",
            "ورزشی",
            "حیوانات",
            "مکان ها",
            "مشاغل",
            "انتزاعی",
            "حس و حال",
            "عمومی",
            "فیلم",
            "مشاهیر",
            "علمی",
            "کتاب",
            "تاریخ",
            "ضرب المثل"

    };
    public static String[] kids = {
            "اشیاء",
            "خوراکی ها",
            "حیوانات",
            "مشاغل",
            "اسباب بازی",
            "حروف الفبا"
    };
    public static void resetScore()
    {
        teamonepoints=0;
        teamtwopoints=0;
        teamthreepoints=0;
        teamfourpoints=0;
        teamonetime=0;
        teamtwotime=0;
        teamthreetime=0;
        teamfourtime=0;
        counter=1;
        period=1;
        currencounter=1;
        witchDoor=1;
        teamonefault=0;
        teamtwofault=0;
        teamthreefault=0;
        teamfourfault=0;
        teamonechangedword=0;
        teamtwochangedword=0;
        teamthreechangedword=0;
        teamfourchangedword=0;
        teamonepoints=0;
        teamtwopoints=0;
        teamthreepoints=0;
        teamfourpoints=0;
        teamonetime=0;
        teamtwotime=0;
        teamthreetime=0;
        teamfourtime=0;
        counter=1;
        period=1;
        currencounter=1;
        witchDoor=1;
        countershomar=1;
        whichuser=1;
        wichteam=1;
    }
    public static void fullreset()
    {
        teamonepoints=0;
        teamtwopoints=0;
        teamthreepoints=0;
        teamfourpoints=0;
        teamonetime=0;
        teamtwotime=0;
        teamthreetime=0;
        teamfourtime=0;
        counter=1;
        period=1;
        currencounter=1;
        witchDoor=1;
        teamonefault=0;
        teamtwofault=0;
        teamthreefault=0;
        teamfourfault=0;
        teamonechangedword=0;
        teamtwochangedword=0;
        teamthreechangedword=0;
        teamfourchangedword=0;
        teamonepoints=0;
        teamtwopoints=0;
        teamthreepoints=0;
        teamfourpoints=0;
        teamonetime=0;
        teamtwotime=0;
        teamthreetime=0;
        teamfourtime=0;
        counter=1;
        period=1;
        currencounter=1;
        witchDoor=1;
        dor=1;
        videoRecord=0;
        Time=0;
        numberofusers=0;
        typeofmatch=0;
        numberOfuserTeams=0;
        countershomar=1;
        whichuser=1;
        wichteam=1;
        twotype=false;
    }


}

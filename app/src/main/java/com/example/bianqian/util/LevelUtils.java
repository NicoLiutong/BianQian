package com.example.bianqian.util;

/**
 * Created by 刘通 on 2017/7/16.
 */

public class LevelUtils {
    private static final int basicEmpiricalValue= 25;

    public static int[] getLevel(int empiricalValue){
        int level, showEmpircalValue, allEmpircalValue;
        if(empiricalValue <= basicEmpiricalValue){
            level = 1;
            showEmpircalValue = empiricalValue;
            allEmpircalValue = basicEmpiricalValue;
            return new int[]{level,showEmpircalValue,allEmpircalValue};
        }else {
            showEmpircalValue = empiricalValue - basicEmpiricalValue;
            allEmpircalValue = basicEmpiricalValue;

            for (int i = 2;;i++){
                level = i;
                allEmpircalValue = allEmpircalValue * 2;
                if(showEmpircalValue <= allEmpircalValue){
                    return new int[]{level,showEmpircalValue,allEmpircalValue};
                }
                showEmpircalValue = showEmpircalValue - allEmpircalValue;
            }
        }
    }
}

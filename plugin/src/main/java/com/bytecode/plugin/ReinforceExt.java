package com.bytecode.plugin;

/**
 * Created by JohnnySwordMan on 2021/4/5
 *
 * Extension需要无参构造函数
 */
public class ReinforceExt {

    String username;
    String password;
    String reinforceToolPath;

    @Override
    public String toString() {
        return "ReinforceExt{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", reinforceToolPath='" + reinforceToolPath + '\'' +
                '}';
    }
}

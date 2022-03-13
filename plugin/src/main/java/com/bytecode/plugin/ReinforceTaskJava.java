package com.bytecode.plugin;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by JohnnySwordMan on 2021/4/5
 */
public class ReinforceTaskJava extends DefaultTask {

    private String name;
    private File apkFile;
    private ReinforceExtension reinforceExt;

    @Inject
    public ReinforceTaskJava(String name, File apk, ReinforceExtension reinforceExt) {
        this.name = name;
        this.apkFile = apk;
        this.reinforceExt = reinforceExt;
        // 任务分组，如果不设置，该任务位于other分组中
        setGroup("reinforce");
    }

    @TaskAction
    public void onReinforceAction() {
        // TODO： 执行加固逻辑
        System.out.println("ReinforceTask---doReinforceAction---name = " + name);
        System.out.println("ReinforceTask---doReinforceAction---apk = " + apkFile);
        System.out.println("ReinforceTask---doReinforceAction---reinforceExt = " + reinforceExt);
    }
}

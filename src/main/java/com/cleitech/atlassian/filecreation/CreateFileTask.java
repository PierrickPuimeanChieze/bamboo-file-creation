package com.cleitech.atlassian.filecreation;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.task.*;
import com.atlassian.bamboo.utils.i18n.I18nBean;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * Created by pierrick.puimean on 1/04/2015.
 */
public class CreateFileTask implements TaskType {
    @Autowired
    public I18nBean i18nBean;
    @NotNull
    @Override
    public TaskResult execute(TaskContext taskContext) throws TaskException {
        final BuildLogger buildLogger = taskContext.getBuildLogger();

        final String fileContent = taskContext.getConfigurationMap().get(CreateFileTaskConfigurator.CONTENT_CONFIG_KEY);

        buildLogger.addBuildLogEntry("File Content"+fileContent);
        final String filePath = taskContext.getConfigurationMap().get(CreateFileTaskConfigurator.FILEPATH_CONFIG_KEY);
        File destinationFile = new File(taskContext.getWorkingDirectory(), filePath);
        File parentDirectory = destinationFile.getParentFile();
        if (!parentDirectory.exists()) {
            parentDirectory.mkdirs();
        } else if (parentDirectory.isDirectory() && !parentDirectory.equals(taskContext.getWorkingDirectory())) {
            throw new TaskException(i18nBean.getText("filecreation.parentnotdir"));
        }
//        destinationFile.
        buildLogger.addBuildLogEntry(destinationFile.getAbsolutePath());

        return TaskResultBuilder.newBuilder(taskContext).success().build();
    }
}

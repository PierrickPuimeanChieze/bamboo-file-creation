package com.cleitech.atlassian.filecreation;

import com.atlassian.bamboo.build.logger.BuildLogger;
import com.atlassian.bamboo.task.*;
import com.atlassian.bamboo.utils.i18n.I18nBean;
import com.atlassian.bamboo.utils.i18n.I18nBeanFactory;
import com.atlassian.bamboo.utils.i18n.I18nBeanFactoryImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by pierrick.puimean on 1/04/2015.
 */
public class CreateFileTask implements TaskType {

    public I18nBeanFactory i18nBeanFactory;

    public CreateFileTask(I18nBeanFactory i18nBeanFactory) {
        this.i18nBeanFactory=i18nBeanFactory;
    }
    @NotNull
    @Override
    public TaskResult execute(TaskContext taskContext) throws TaskException {

        final BuildLogger buildLogger = taskContext.getBuildLogger();

        //We retrieve the file content from the configuration
        final String fileContent = taskContext.getConfigurationMap().get(CreateFileTaskConfigurator.CONTENT_CONFIG_KEY);

        buildLogger.addBuildLogEntry("File Content :"+fileContent);
        //Same for the path
        final String filePath = taskContext.getConfigurationMap().get(CreateFileTaskConfigurator.FILEPATH_CONFIG_KEY);
        File destinationFile = new File(taskContext.getWorkingDirectory(), filePath);
        File parentDirectory = destinationFile.getParentFile();
        I18nBean i18nBean = i18nBeanFactory.getI18nBean();
        if (destinationFile.exists()) {
            throw new TaskException(i18nBean.getText("filecreation.fileExists.error", new Object[]{destinationFile.getAbsolutePath()}));
        }
        //If the parent directory doesn't exists we try to created it
        if (!parentDirectory.exists()) {
            //If it is impossible, we throw an Exception
            if (!parentDirectory.mkdirs()) {
                throw new TaskException(i18nBean.getText("filecreation.parent.unableToCreate.error", new Object[]{parentDirectory.getAbsolutePath()}));
            }
        //But if it is exists, but is in fact NOT a dir, we also throw an exception
        } else if (!parentDirectory.isDirectory()) {
            throw new TaskException(i18nBean.getText("filecreation.parent.notADirectory.error", new Object[]{parentDirectory.getAbsolutePath()}));
        }
        try (PrintWriter printWriter = new PrintWriter(destinationFile)){
            printWriter.write(fileContent);
        } catch (FileNotFoundException e) {
            throw new TaskException(i18nBean.getText("filecreation.unableToWrite.error", new Object[]{destinationFile.getAbsolutePath()}));
        }

        buildLogger.addBuildLogEntry(destinationFile.getAbsolutePath());

        return TaskResultBuilder.newBuilder(taskContext).success().build();
    }
}

package com.cleitech.atlassian.filecreation;

import com.atlassian.bamboo.collections.ActionParametersMap;
import com.atlassian.bamboo.task.AbstractTaskConfigurator;
import com.atlassian.bamboo.task.TaskDefinition;
import com.atlassian.bamboo.utils.error.ErrorCollection;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Created by pierrick.puimean on 1/04/2015.
 */
public class CreateFileTaskConfigurator extends AbstractTaskConfigurator {


    public static final String CONTENT_CONFIG_KEY = "content";
    public static final String FILEPATH_CONFIG_KEY = "filepath";
    public static final String OVERWRITE_CONFIG_KEY="overwrite";
    @NotNull
    @Override
    public Map<String, String> generateTaskConfigMap(@NotNull final ActionParametersMap params, @Nullable final TaskDefinition previousTaskDefinition)
    {
        final Map<String, String> config = super.generateTaskConfigMap(params, previousTaskDefinition);

        config.put(CONTENT_CONFIG_KEY, params.getString(CONTENT_CONFIG_KEY));
        config.put(FILEPATH_CONFIG_KEY, params.getString(FILEPATH_CONFIG_KEY));
        config.put(OVERWRITE_CONFIG_KEY, params.getString(OVERWRITE_CONFIG_KEY));
        return config;
    }

    @Override
    public void populateContextForCreate(@NotNull final Map<String, Object> context)
    {
        super.populateContextForCreate(context);

        context.put(CONTENT_CONFIG_KEY, "File Content Here");
        context.put(FILEPATH_CONFIG_KEY, "File Path Here");
    }

    @Override
    public void populateContextForEdit(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition)
    {
        super.populateContextForEdit(context, taskDefinition);

        context.put(CONTENT_CONFIG_KEY, taskDefinition.getConfiguration().get(CONTENT_CONFIG_KEY));
        context.put(FILEPATH_CONFIG_KEY, taskDefinition.getConfiguration().get(FILEPATH_CONFIG_KEY));
        context.put(OVERWRITE_CONFIG_KEY, taskDefinition.getConfiguration().get(OVERWRITE_CONFIG_KEY));
    }

    @Override
    public void populateContextForView(@NotNull final Map<String, Object> context, @NotNull final TaskDefinition taskDefinition)
    {
        super.populateContextForView(context, taskDefinition);
        context.put(CONTENT_CONFIG_KEY, taskDefinition.getConfiguration().get(CONTENT_CONFIG_KEY));
        context.put(FILEPATH_CONFIG_KEY, taskDefinition.getConfiguration().get(FILEPATH_CONFIG_KEY));
        context.put(OVERWRITE_CONFIG_KEY, taskDefinition.getConfiguration().get(OVERWRITE_CONFIG_KEY));
    }

    @Override
    public void validate(@NotNull final ActionParametersMap params, @NotNull final ErrorCollection errorCollection)
    {
        super.validate(params, errorCollection);

        final String contentValue = params.getString(CONTENT_CONFIG_KEY);
        if (StringUtils.isEmpty(contentValue))
        {
            errorCollection.addError(CONTENT_CONFIG_KEY, getI18nBean().getText("filecreation.content.error"));
        }
        final String filePath = params.getString(FILEPATH_CONFIG_KEY);
        if (StringUtils.isEmpty(filePath))
        {
            errorCollection.addError(FILEPATH_CONFIG_KEY, getI18nBean().getText("filecreation.filepath.error"));
        }
    }


}

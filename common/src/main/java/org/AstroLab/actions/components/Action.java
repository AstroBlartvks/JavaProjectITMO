package org.AstroLab.actions.components;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import org.AstroLab.actions.utils.TypesOfActions;

@Getter
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "actionType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ActionAdd.class, name = "ADD"),
        @JsonSubTypes.Type(value = ActionAddIfMax.class, name = "ADD_IF_MAX"),
        @JsonSubTypes.Type(value = ActionAddIfMin.class, name = "ADD_IF_MIN"),
        @JsonSubTypes.Type(value = ActionClear.class, name = "CLEAR"),
        @JsonSubTypes.Type(value = ActionCountByDistance.class, name = "COUNT_BY_DISTANCE"),
        @JsonSubTypes.Type(value = ActionCountGreaterThanDistance.class, name = "COUNT_GREATER_THAN_DISTANCE"),
        @JsonSubTypes.Type(value = ActionHelp.class, name = "HELP"),
        @JsonSubTypes.Type(value = ActionInfo.class, name = "INFO"),
        @JsonSubTypes.Type(value = ActionPrintFieldDescendingDistance.class, name = "PRINT_FIELD_DESCENDING_DISTANCE"),
        @JsonSubTypes.Type(value = ActionRemoveById.class, name = "REMOVE_BY_ID"),
        @JsonSubTypes.Type(value = ActionRemoveGreater.class, name = "REMOVE_GREATER"),
        @JsonSubTypes.Type(value = ActionShow.class, name = "SHOW"),
        @JsonSubTypes.Type(value = ActionUpdate.class, name = "UPDATE")
})
public abstract class Action {
    protected TypesOfActions actionType;

    @Override
    public String toString() {
        return "Action{" +
                "actionType=" + actionType +
                '}';
    }
}

package AstroLab.actions.utils;

public enum ActionsName {
    /**.
     * Action: add
     */
    ADD("ADD"),
    /**.
     * Action: add_if_max
     */
    ADD_IF_MAX("ADD_IF_MAX"),
    /**.
     * Action: add_if_min
     */
    ADD_IF_MIN("ADD_IF_MIN"),
    /**.
     * Action: clear
     */
    CLEAR("CLEAR"),
    /**.
     * Action: count_by_distance
     */
    COUNT_BY_DISTANCE("COUNT_BY_DISTANCE"),
    /**.
     * Action: count_greater_tan_distance
     */
    COUNT_GREATER_THAN_DISTANCE("COUNT_GREATER_THAN_DISTANCE"),
    /**.
     * Action: help
     */
    HELP("HELP"),
    /**.
     * Action: info
     */
    INFO("INFO"),
    /**.
     * Action: print field descending distance
     */
    PRINT_FIELD_DESCENDING_DISTANCE("PRINT_FIELD_DESCENDING_DISTANCE"),
    /**.
     * Action: remove_by_id
     */
    REMOVE_BY_ID("REMOVE_BY_ID"),
    /**.
     * Action: remove_greater
     */
    REMOVE_GREATER("REMOVE_GREATER"),
    /**.
     * Action: show
     */
    SHOW("SHOW"),
    /**.
     * Action: update
     */
    UPDATE("UPDATE"),
    /**.
     * Action: exit
     */
    EXIT("EXIT");

    ActionsName(final String jsonName) {}
}

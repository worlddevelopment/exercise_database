//
// Translated by CS2J (http://www.cs2j.com): 1/30/2014 10:47:50 AM
//

package DIaLOGIKa.b2xtranslator.OpenXmlLib.SpreadsheetML;


/*
 * Copyright (c) 2009, DIaLOGIKa
 *
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright 
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright 
 *       notice, this list of conditions and the following disclaimer in the 
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the names of copyright holders, nor the names of its contributors 
 *       may be used to endorse or promote products derived from this software 
 *       without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, 
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */
public class Sml   
{
    public static final String Ns = "http://schemas.openxmlformats.org/spreadsheetml/2006/main";
    public static class AutoFilter   
    {
        /**
        * AutoFilter Column
        */
        public static final String ElFilterColumn = "filterColumn";
        /**
        * Sort State for Auto Filter
        */
        public static final String ElSortState = "sortState";
        /**
        * 
        */
        public static final String ElExtLst = "extLst";
        /**
        * Filter Criteria
        */
        public static final String ElFilters = "filters";
        /**
        * Top 10
        */
        public static final String ElTop10 = "top10";
        /**
        * Custom Filters
        */
        public static final String ElCustomFilters = "customFilters";
        /**
        * Dynamic Filter
        */
        public static final String ElDynamicFilter = "dynamicFilter";
        /**
        * Color Filter Criteria
        */
        public static final String ElColorFilter = "colorFilter";
        /**
        * Icon Filter
        */
        public static final String ElIconFilter = "iconFilter";
        /**
        * Filter
        */
        public static final String ElFilter = "filter";
        /**
        * Date Grouping
        */
        public static final String ElDateGroupItem = "dateGroupItem";
        /**
        * Custom Filter Criteria
        */
        public static final String ElCustomFilter = "customFilter";
        /**
        * Sort Condition
        */
        public static final String ElSortCondition = "sortCondition";
        /**
        * Cell or Range Reference
        */
        public static final String AttrRef = "ref";
        /**
        * Filter Column Data
        */
        public static final String AttrColId = "colId";
        /**
        * Hidden AutoFilter Button
        */
        public static final String AttrHiddenButton = "hiddenButton";
        /**
        * Show Filter Button
        */
        public static final String AttrShowButton = "showButton";
        /**
        * Filter by Blank
        */
        public static final String AttrBlank = "blank";
        /**
        * Calendar Type
        */
        public static final String AttrCalendarType = "calendarType";
        /**
        * Filter Value
        */
        public static final String AttrVal = "val";
        /**
        * And
        */
        public static final String AttrAnd = "and";
        /**
        * Filter Comparison Operator
        */
        public static final String AttrOperator = "operator";
        /**
        * Top
        */
        public static final String AttrTop = "top";
        /**
        * Filter by Percent
        */
        public static final String AttrPercent = "percent";
        /**
        * Filter Value
        */
        public static final String AttrFilterVal = "filterVal";
        /**
        * Differential Format Record Id
        */
        public static final String AttrDxfId = "dxfId";
        /**
        * Filter By Cell Color
        */
        public static final String AttrCellColor = "cellColor";
        /**
        * Icon Set
        */
        public static final String AttrIconSet = "iconSet";
        /**
        * Icon Id
        */
        public static final String AttrIconId = "iconId";
        /**
        * Dynamic filter type
        */
        public static final String AttrType = "type";
        /**
        * Max Value
        */
        public static final String AttrMaxVal = "maxVal";
        /**
        * Sort by Columns
        */
        public static final String AttrColumnSort = "columnSort";
        /**
        * Case Sensitive
        */
        public static final String AttrCaseSensitive = "caseSensitive";
        /**
        * Sort Method
        */
        public static final String AttrSortMethod = "sortMethod";
        /**
        * Descending
        */
        public static final String AttrDescending = "descending";
        /**
        * Sort By
        */
        public static final String AttrSortBy = "sortBy";
        /**
        * Custom List
        */
        public static final String AttrCustomList = "customList";
        /**
        * Year
        */
        public static final String AttrYear = "year";
        /**
        * Month
        */
        public static final String AttrMonth = "month";
        /**
        * Day
        */
        public static final String AttrDay = "day";
        /**
        * Hour
        */
        public static final String AttrHour = "hour";
        /**
        * Minute
        */
        public static final String AttrMinute = "minute";
        /**
        * Second
        */
        public static final String AttrSecond = "second";
        /**
        * Date Time Grouping
        */
        public static final String AttrDateTimeGrouping = "dateTimeGrouping";
    }

    public static class BaseTypes   
    {
        /**
        * Extension
        */
        public static final String ElExt = "ext";
        /**
        * Value
        */
        public static final String AttrV = "v";
        /**
        * URI
        */
        public static final String AttrUri = "uri";
    }

    public static class CalculationChain   
    {
        /**
        * Calculation Chain Info
        */
        public static final String ElCalcChain = "calcChain";
        /**
        * Cell
        */
        public static final String ElC = "c";
        /**
        * 
        */
        public static final String ElExtLst = "extLst";
        /**
        * Cell Reference
        */
        public static final String AttrR = "r";
        /**
        * Sheet Id
        */
        public static final String AttrI = "i";
        /**
        * Child Chain
        */
        public static final String AttrS = "s";
        /**
        * New Dependency Level
        */
        public static final String AttrL = "l";
        /**
        * New Thread
        */
        public static final String AttrT = "t";
        /**
        * Array
        */
        public static final String AttrA = "a";
    }

    public static class Comments   
    {
        /**
        * Comments
        */
        public static final String ElComments = "comments";
        /**
        * Authors
        */
        public static final String ElAuthors = "authors";
        /**
        * List of Comments
        */
        public static final String ElCommentList = "commentList";
        /**
        * 
        */
        public static final String ElExtLst = "extLst";
        /**
        * Author
        */
        public static final String ElAuthor = "author";
        /**
        * Comment
        */
        public static final String ElComment = "comment";
        /**
        * Comment Text
        */
        public static final String ElText = "text";
        /**
        * Cell Reference
        */
        public static final String AttrRef = "ref";
        /**
        * Author Id
        */
        public static final String AttrAuthorId = "authorId";
        /**
        * Unique Identifier for Comment
        */
        public static final String AttrGuid = "guid";
    }

    public static class CustomXmlMappings   
    {
        /**
        * XML Mapping
        */
        public static final String ElMapInfo = "MapInfo";
        /**
        * XML Schema
        */
        public static final String ElSchema = "Schema";
        /**
        * XML Mapping Properties
        */
        public static final String ElMap = "Map";
        /**
        * XML Mapping
        */
        public static final String ElDataBinding = "DataBinding";
        /**
        * Prefix Mappings for XPath Expressions
        */
        public static final String AttrSelectionNamespaces = "SelectionNamespaces";
        /**
        * Schema ID
        */
        public static final String AttrID = "ID";
        /**
        * Schema Reference
        */
        public static final String AttrSchemaRef = "SchemaRef";
        /**
        * Schema Root Namespace
        */
        public static final String AttrNamespace = "Namespace";
        /**
        * XML Mapping Name
        */
        public static final String AttrName = "Name";
        /**
        * Root Element Name
        */
        public static final String AttrRootElement = "RootElement";
        /**
        * Schema Name
        */
        public static final String AttrSchemaID = "SchemaID";
        /**
        * Show Validation Errors
        */
        public static final String AttrShowImportExportValidationErrors = "ShowImportExportValidationErrors";
        /**
        * AutoFit Table on Refresh
        */
        public static final String AttrAutoFit = "AutoFit";
        /**
        * Append Data to Table
        */
        public static final String AttrAppend = "Append";
        /**
        * Preserve AutoFilter State
        */
        public static final String AttrPreserveSortAFLayout = "PreserveSortAFLayout";
        /**
        * Preserve Cell Formatting
        */
        public static final String AttrPreserveFormat = "PreserveFormat";
        /**
        * Unique Identifer
        */
        public static final String AttrDataBindingName = "DataBindingName";
        /**
        * Binding to External File
        */
        public static final String AttrFileBinding = "FileBinding";
        /**
        * Reference to Connection ID
        */
        public static final String AttrConnectionID = "ConnectionID";
        /**
        * File Binding Name
        */
        public static final String AttrFileBindingName = "FileBindingName";
        /**
        * XML Data Loading Behavior
        */
        public static final String AttrDataBindingLoadMode = "DataBindingLoadMode";
    }

    public static class ExternalConnections   
    {
        /**
        * Connections
        */
        public static final String ElConnections = "connections";
        /**
        * Connection
        */
        public static final String ElConnection = "connection";
        /// <summary>
        /// ODBC & OLE DB Properties
        /// </summary>
        public static final String ElDbPr = "dbPr";
        /**
        * OLAP Properties
        */
        public static final String ElOlapPr = "olapPr";
        /**
        * Web Query Properties
        */
        public static final String ElWebPr = "webPr";
        /**
        * Text Import Settings
        */
        public static final String ElTextPr = "textPr";
        /**
        * Query Parameters
        */
        public static final String ElParameters = "parameters";
        /**
        * Future Feature Data Storage
        */
        public static final String ElExtLst = "extLst";
        /**
        * Tables
        */
        public static final String ElTables = "tables";
        /**
        * Parameter Properties
        */
        public static final String ElParameter = "parameter";
        /**
        * No Value
        */
        public static final String ElM = "m";
        /**
        * Character Value
        */
        public static final String ElS = "s";
        /**
        * Index
        */
        public static final String ElX = "x";
        /**
        * Fields
        */
        public static final String ElTextFields = "textFields";
        /**
        * Text Import Field Settings
        */
        public static final String ElTextField = "textField";
        /**
        * Connection Id
        */
        public static final String AttrId = "id";
        /**
        * Source Database File
        */
        public static final String AttrSourceFile = "sourceFile";
        /**
        * Connection File
        */
        public static final String AttrOdcFile = "odcFile";
        /**
        * Keep Connection Open
        */
        public static final String AttrKeepAlive = "keepAlive";
        /**
        * Automatic Refresh Interval
        */
        public static final String AttrInterval = "interval";
        /**
        * Connection Name
        */
        public static final String AttrName = "name";
        /**
        * Connection Description
        */
        public static final String AttrDescription = "description";
        /**
        * Database Source Type
        */
        public static final String AttrType = "type";
        /**
        * Reconnection Method
        */
        public static final String AttrReconnectionMethod = "reconnectionMethod";
        /**
        * Last Refresh Version
        */
        public static final String AttrRefreshedVersion = "refreshedVersion";
        /**
        * Minimum Version Required for Refresh
        */
        public static final String AttrMinRefreshableVersion = "minRefreshableVersion";
        /**
        * Save Password
        */
        public static final String AttrSavePassword = "savePassword";
        /**
        * New Connection
        */
        public static final String AttrNew = "new";
        /**
        * Deleted Connection
        */
        public static final String AttrDeleted = "deleted";
        /**
        * Only Use Connection File
        */
        public static final String AttrOnlyUseConnectionFile = "onlyUseConnectionFile";
        /**
        * Background Refresh
        */
        public static final String AttrBackground = "background";
        /**
        * Refresh on Open
        */
        public static final String AttrRefreshOnLoad = "refreshOnLoad";
        /**
        * Save Data
        */
        public static final String AttrSaveData = "saveData";
        /**
        * Reconnection Method
        */
        public static final String AttrCredentials = "credentials";
        /**
        * SSO Id
        */
        public static final String AttrSingleSignOnId = "singleSignOnId";
        /**
        * Command Text
        */
        public static final String AttrCommand = "command";
        /**
        * Command Text
        */
        public static final String AttrServerCommand = "serverCommand";
        /**
        * OLE DB Command Type
        */
        public static final String AttrCommandType = "commandType";
        /**
        * Local Cube
        */
        public static final String AttrLocal = "local";
        /**
        * Local Cube Connection
        */
        public static final String AttrLocalConnection = "localConnection";
        /**
        * Local Refresh
        */
        public static final String AttrLocalRefresh = "localRefresh";
        /**
        * Send Locale to OLAP
        */
        public static final String AttrSendLocale = "sendLocale";
        /**
        * Drill Through Count
        */
        public static final String AttrRowDrillCount = "rowDrillCount";
        /**
        * OLAP Fill Formatting
        */
        public static final String AttrServerFill = "serverFill";
        /**
        * OLAP Number Format
        */
        public static final String AttrServerNumberFormat = "serverNumberFormat";
        /**
        * OLAP Server Font
        */
        public static final String AttrServerFont = "serverFont";
        /**
        * OLAP Font Formatting
        */
        public static final String AttrServerFontColor = "serverFontColor";
        /**
        * XML Source
        */
        public static final String AttrXml = "xml";
        /**
        * Import XML Source Data
        */
        public static final String AttrSourceData = "sourceData";
        /**
        * Parse PRE
        */
        public static final String AttrParsePre = "parsePre";
        /**
        * Consecutive Delimiters
        */
        public static final String AttrConsecutive = "consecutive";
        /**
        * Use First Row
        */
        public static final String AttrFirstRow = "firstRow";
        /**
        * Created in Excel 97
        */
        public static final String AttrXl97 = "xl97";
        /**
        * Dates as Text
        */
        public static final String AttrTextDates = "textDates";
        /**
        * Refreshed in Excel 2000
        */
        public static final String AttrXl2000 = "xl2000";
        /**
        * URL
        */
        public static final String AttrUrl = "url";
        /**
        * Web Post
        */
        public static final String AttrPost = "post";
        /**
        * HTML Tables Only
        */
        public static final String AttrHtmlTables = "htmlTables";
        /**
        * HTML Formatting Handling
        */
        public static final String AttrHtmlFormat = "htmlFormat";
        /**
        * Edit Query URL
        */
        public static final String AttrEditPage = "editPage";
        /**
        * Parameter Count
        */
        public static final String AttrCount = "count";
        /**
        * SQL Data Type
        */
        public static final String AttrSqlType = "sqlType";
        /**
        * Parameter Type
        */
        public static final String AttrParameterType = "parameterType";
        /**
        * Refresh on Change
        */
        public static final String AttrRefreshOnChange = "refreshOnChange";
        /**
        * Parameter Prompt String
        */
        public static final String AttrPrompt = "prompt";
        /**
        * Boolean
        */
        public static final String AttrBoolean = "boolean";
        /**
        * Double
        */
        public static final String AttrDouble = "double";
        /**
        * Integer
        */
        public static final String AttrInteger = "integer";
        /**
        * String
        */
        public static final String AttrString = "string";
        /**
        * Cell Reference
        */
        public static final String AttrCell = "cell";
        /**
        * File Type
        */
        public static final String AttrFileType = "fileType";
        /**
        * Code Page
        */
        public static final String AttrCodePage = "codePage";
        /**
        * Delimited File
        */
        public static final String AttrDelimited = "delimited";
        /**
        * Decimal Separator
        */
        public static final String AttrDecimal = "decimal";
        /**
        * Thousands Separator
        */
        public static final String AttrThousands = "thousands";
        /**
        * Tab as Delimiter
        */
        public static final String AttrTab = "tab";
        /**
        * Space is Delimiter
        */
        public static final String AttrSpace = "space";
        /**
        * Comma is Delimiter
        */
        public static final String AttrComma = "comma";
        /**
        * Semicolon is Delimiter
        */
        public static final String AttrSemicolon = "semicolon";
        /**
        * Qualifier
        */
        public static final String AttrQualifier = "qualifier";
        /**
        * Custom Delimiter
        */
        public static final String AttrDelimiter = "delimiter";
        /**
        * Position
        */
        public static final String AttrPosition = "position";
    }

    public static class PivotTable   
    {
        /**
        * PivotCache Definition
        */
        public static final String ElPivotCacheDefinition = "pivotCacheDefinition";
        /**
        * PivotCache Records
        */
        public static final String ElPivotCacheRecords = "pivotCacheRecords";
        /**
        * PivotTable Definition
        */
        public static final String ElPivotTableDefinition = "pivotTableDefinition";
        /**
        * PivotCache Source Description
        */
        public static final String ElCacheSource = "cacheSource";
        /**
        * PivotCache Fields
        */
        public static final String ElCacheFields = "cacheFields";
        /**
        * PivotCache Hierarchies
        */
        public static final String ElCacheHierarchies = "cacheHierarchies";
        /**
        * OLAP KPIs
        */
        public static final String ElKpis = "kpis";
        /**
        * Tuple Cache
        */
        public static final String ElTupleCache = "tupleCache";
        /**
        * Calculated Items
        */
        public static final String ElCalculatedItems = "calculatedItems";
        /**
        * Calculated Members
        */
        public static final String ElCalculatedMembers = "calculatedMembers";
        /**
        * OLAP Dimensions
        */
        public static final String ElDimensions = "dimensions";
        /**
        * OLAP Measure Groups
        */
        public static final String ElMeasureGroups = "measureGroups";
        /**
        * OLAP Measure Group
        */
        public static final String ElMaps = "maps";
        /**
        * Future Feature Data Storage Area
        */
        public static final String ElExtLst = "extLst";
        /**
        * PivotCache Field
        */
        public static final String ElCacheField = "cacheField";
        /**
        * Shared Items
        */
        public static final String ElSharedItems = "sharedItems";
        /**
        * Field Group Properties
        */
        public static final String ElFieldGroup = "fieldGroup";
        /**
        * Member Properties Map
        */
        public static final String ElMpMap = "mpMap";
        /**
        * Worksheet PivotCache Source
        */
        public static final String ElWorksheetSource = "worksheetSource";
        /**
        * Consolidation Source
        */
        public static final String ElConsolidation = "consolidation";
        /**
        * Page Item Values
        */
        public static final String ElPages = "pages";
        /**
        * Range Sets
        */
        public static final String ElRangeSets = "rangeSets";
        /**
        * Page Items
        */
        public static final String ElPage = "page";
        /**
        * Page Item
        */
        public static final String ElPageItem = "pageItem";
        /**
        * Range Set
        */
        public static final String ElRangeSet = "rangeSet";
        /**
        * No Value
        */
        public static final String ElM = "m";
        /**
        * Numeric
        */
        public static final String ElN = "n";
        /**
        * Boolean
        */
        public static final String ElB = "b";
        /**
        * Error Value
        */
        public static final String ElE = "e";
        /**
        * Character Value
        */
        public static final String ElS = "s";
        /**
        * Date Time
        */
        public static final String ElD = "d";
        /**
        * Tuples
        */
        public static final String ElTpls = "tpls";
        /**
        * Member Property Indexes
        */
        public static final String ElX = "x";
        /**
        * Range Grouping Properties
        */
        public static final String ElRangePr = "rangePr";
        /**
        * Discrete Grouping Properties
        */
        public static final String ElDiscretePr = "discretePr";
        /**
        * OLAP Group Items
        */
        public static final String ElGroupItems = "groupItems";
        /**
        * PivotCache Record
        */
        public static final String ElR = "r";
        /**
        * OLAP KPI
        */
        public static final String ElKpi = "kpi";
        /**
        * PivotCache Hierarchy
        */
        public static final String ElCacheHierarchy = "cacheHierarchy";
        /**
        * Fields Usage
        */
        public static final String ElFieldsUsage = "fieldsUsage";
        /**
        * OLAP Grouping Levels
        */
        public static final String ElGroupLevels = "groupLevels";
        /**
        * PivotCache Field Id
        */
        public static final String ElFieldUsage = "fieldUsage";
        /**
        * OLAP Grouping Levels
        */
        public static final String ElGroupLevel = "groupLevel";
        /**
        * OLAP Level Groups
        */
        public static final String ElGroups = "groups";
        /**
        * OLAP Group
        */
        public static final String ElGroup = "group";
        /**
        * OLAP Group Members
        */
        public static final String ElGroupMembers = "groupMembers";
        /**
        * OLAP Group Member
        */
        public static final String ElGroupMember = "groupMember";
        /**
        * Entries
        */
        public static final String ElEntries = "entries";
        /**
        * Sets
        */
        public static final String ElSets = "sets";
        /**
        * OLAP Query Cache
        */
        public static final String ElQueryCache = "queryCache";
        /**
        * Server Formats
        */
        public static final String ElServerFormats = "serverFormats";
        /**
        * Server Format
        */
        public static final String ElServerFormat = "serverFormat";
        /**
        * Tuple
        */
        public static final String ElTpl = "tpl";
        /**
        * OLAP Set
        */
        public static final String ElSet = "set";
        /**
        * Sort By Tuple
        */
        public static final String ElSortByTuple = "sortByTuple";
        /**
        * Query
        */
        public static final String ElQuery = "query";
        /**
        * Calculated Item
        */
        public static final String ElCalculatedItem = "calculatedItem";
        /**
        * Calculated Item Location
        */
        public static final String ElPivotArea = "pivotArea";
        /**
        * Calculated Member
        */
        public static final String ElCalculatedMember = "calculatedMember";
        /**
        * PivotTable Location
        */
        public static final String ElLocation = "location";
        /**
        * PivotTable Fields
        */
        public static final String ElPivotFields = "pivotFields";
        /**
        * Row Fields
        */
        public static final String ElRowFields = "rowFields";
        /**
        * Row Items
        */
        public static final String ElRowItems = "rowItems";
        /**
        * Column Fields
        */
        public static final String ElColFields = "colFields";
        /**
        * Column Items
        */
        public static final String ElColItems = "colItems";
        /**
        * Page Field Items
        */
        public static final String ElPageFields = "pageFields";
        /**
        * Data Fields
        */
        public static final String ElDataFields = "dataFields";
        /**
        * PivotTable Formats
        */
        public static final String ElFormats = "formats";
        /**
        * Conditional Formats
        */
        public static final String ElConditionalFormats = "conditionalFormats";
        /**
        * PivotChart Formats
        */
        public static final String ElChartFormats = "chartFormats";
        /**
        * PivotTable OLAP Hierarchies
        */
        public static final String ElPivotHierarchies = "pivotHierarchies";
        /**
        * PivotTable Style
        */
        public static final String ElPivotTableStyleInfo = "pivotTableStyleInfo";
        /**
        * Filters
        */
        public static final String ElFilters = "filters";
        /**
        * Row OLAP Hierarchy References
        */
        public static final String ElRowHierarchiesUsage = "rowHierarchiesUsage";
        /**
        * Column OLAP Hierarchy References
        */
        public static final String ElColHierarchiesUsage = "colHierarchiesUsage";
        /**
        * PivotTable Field
        */
        public static final String ElPivotField = "pivotField";
        /**
        * Field Items
        */
        public static final String ElItems = "items";
        /**
        * AutoSort Scope
        */
        public static final String ElAutoSortScope = "autoSortScope";
        /**
        * PivotTable Field Item
        */
        public static final String ElItem = "item";
        /**
        * Page Field
        */
        public static final String ElPageField = "pageField";
        /**
        * Data Field Item
        */
        public static final String ElDataField = "dataField";
        /**
        * Row Items
        */
        public static final String ElI = "i";
        /**
        * Row Items
        */
        public static final String ElField = "field";
        /**
        * PivotTable Format
        */
        public static final String ElFormat = "format";
        /**
        * Conditional Formatting
        */
        public static final String ElConditionalFormat = "conditionalFormat";
        /**
        * Pivot Areas
        */
        public static final String ElPivotAreas = "pivotAreas";
        /**
        * PivotChart Format
        */
        public static final String ElChartFormat = "chartFormat";
        /**
        * OLAP Hierarchy
        */
        public static final String ElPivotHierarchy = "pivotHierarchy";
        /**
        * OLAP Member Properties
        */
        public static final String ElMps = "mps";
        /**
        * Members
        */
        public static final String ElMembers = "members";
        /**
        * Row OLAP Hierarchies
        */
        public static final String ElRowHierarchyUsage = "rowHierarchyUsage";
        /**
        * Column OLAP Hierarchies
        */
        public static final String ElColHierarchyUsage = "colHierarchyUsage";
        /**
        * OLAP Member Property
        */
        public static final String ElMp = "mp";
        /**
        * Member
        */
        public static final String ElMember = "member";
        /**
        * OLAP Dimension
        */
        public static final String ElDimension = "dimension";
        /**
        * OLAP Measure Group
        */
        public static final String ElMeasureGroup = "measureGroup";
        /**
        * OLAP Measure Group
        */
        public static final String ElMap = "map";
        /**
        * PivotTable Advanced Filter
        */
        public static final String ElFilter = "filter";
        /**
        * Auto Filter
        */
        public static final String ElAutoFilter = "autoFilter";
        /**
        * Invalid Cache
        */
        public static final String AttrInvalid = "invalid";
        /**
        * Save Pivot Records
        */
        public static final String AttrSaveData = "saveData";
        /**
        * Refresh On Load
        */
        public static final String AttrRefreshOnLoad = "refreshOnLoad";
        /**
        * Optimize Cache for Memory
        */
        public static final String AttrOptimizeMemory = "optimizeMemory";
        /**
        * Enable PivotCache Refresh
        */
        public static final String AttrEnableRefresh = "enableRefresh";
        /**
        * Last Refreshed By
        */
        public static final String AttrRefreshedBy = "refreshedBy";
        /**
        * PivotCache Last Refreshed Date
        */
        public static final String AttrRefreshedDate = "refreshedDate";
        /**
        * Background Query
        */
        public static final String AttrBackgroundQuery = "backgroundQuery";
        /**
        * Missing Items Limit
        */
        public static final String AttrMissingItemsLimit = "missingItemsLimit";
        /**
        * PivotCache Created Version
        */
        public static final String AttrCreatedVersion = "createdVersion";
        /**
        * PivotCache Last Refreshed Version
        */
        public static final String AttrRefreshedVersion = "refreshedVersion";
        /**
        * Minimum Version Required for Refresh
        */
        public static final String AttrMinRefreshableVersion = "minRefreshableVersion";
        /**
        * PivotCache Record Count
        */
        public static final String AttrRecordCount = "recordCount";
        /**
        * Upgrade PivotCache on Refresh
        */
        public static final String AttrUpgradeOnRefresh = "upgradeOnRefresh";
        /**
        * Supports Subqueries
        */
        public static final String AttrSupportSubquery = "supportSubquery";
        /**
        * Supports Attribute Drilldown
        */
        public static final String AttrSupportAdvancedDrill = "supportAdvancedDrill";
        /**
        * Field Count
        */
        public static final String AttrCount = "count";
        /**
        * PivotCache Field Name
        */
        public static final String AttrName = "name";
        /**
        * PivotCache Field Caption
        */
        public static final String AttrCaption = "caption";
        /**
        * Property Name
        */
        public static final String AttrPropertyName = "propertyName";
        /**
        * Server-based Field
        */
        public static final String AttrServerField = "serverField";
        /**
        * Unique List Retrieved
        */
        public static final String AttrUniqueList = "uniqueList";
        /**
        * Number Format Id
        */
        public static final String AttrNumFmtId = "numFmtId";
        /**
        * Calculated Field Formula
        */
        public static final String AttrFormula = "formula";
        /**
        * SQL Data Type
        */
        public static final String AttrSqlType = "sqlType";
        /**
        * Hierarchy
        */
        public static final String AttrHierarchy = "hierarchy";
        /**
        * Hierarchy Level
        */
        public static final String AttrLevel = "level";
        /**
        * Database Field
        */
        public static final String AttrDatabaseField = "databaseField";
        /**
        * Member Property Count
        */
        public static final String AttrMappingCount = "mappingCount";
        /**
        * Member Property Field
        */
        public static final String AttrMemberPropertyField = "memberPropertyField";
        /**
        * Cache Type
        */
        public static final String AttrType = "type";
        /**
        * Connection Index
        */
        public static final String AttrConnectionId = "connectionId";
        /**
        * Reference
        */
        public static final String AttrRef = "ref";
        /**
        * Sheet Name
        */
        public static final String AttrSheet = "sheet";
        /**
        * Auto Page
        */
        public static final String AttrAutoPage = "autoPage";
        /**
        * Field Item Index Page 1
        */
        public static final String AttrI1 = "i1";
        /**
        * Field Item Index Page 2
        */
        public static final String AttrI2 = "i2";
        /**
        * Field Item index Page 3
        */
        public static final String AttrI3 = "i3";
        /**
        * Field Item Index Page 4
        */
        public static final String AttrI4 = "i4";
        /**
        * Contains Semi Mixed Data Types
        */
        public static final String AttrContainsSemiMixedTypes = "containsSemiMixedTypes";
        /**
        * Contains Non Date
        */
        public static final String AttrContainsNonDate = "containsNonDate";
        /**
        * Contains Date
        */
        public static final String AttrContainsDate = "containsDate";
        /**
        * Contains String
        */
        public static final String AttrContainsString = "containsString";
        /**
        * Contains Blank
        */
        public static final String AttrContainsBlank = "containsBlank";
        /**
        * Contains Mixed Data Types
        */
        public static final String AttrContainsMixedTypes = "containsMixedTypes";
        /**
        * Contains Numbers
        */
        public static final String AttrContainsNumber = "containsNumber";
        /**
        * Contains Integer
        */
        public static final String AttrContainsInteger = "containsInteger";
        /**
        * Minimum Numeric Value
        */
        public static final String AttrMinValue = "minValue";
        /**
        * Maximum Numeric Value
        */
        public static final String AttrMaxValue = "maxValue";
        /**
        * Minimum Date Time
        */
        public static final String AttrMinDate = "minDate";
        /**
        * Maximum Date Time Value
        */
        public static final String AttrMaxDate = "maxDate";
        /**
        * Long Text
        */
        public static final String AttrLongText = "longText";
        /**
        * Unused Item
        */
        public static final String AttrU = "u";
        /**
        * Calculated Item
        */
        public static final String AttrF = "f";
        /**
        * Caption
        */
        public static final String AttrC = "c";
        /**
        * Member Property Count
        */
        public static final String AttrCp = "cp";
        /**
        * Format Index
        */
        public static final String AttrIn = "in";
        /**
        * background Color
        */
        public static final String AttrBc = "bc";
        /**
        * Foreground Color
        */
        public static final String AttrFc = "fc";
        /**
        * Underline
        */
        public static final String AttrUn = "un";
        /**
        * Strikethrough
        */
        public static final String AttrSt = "st";
        /**
        * Value
        */
        public static final String AttrV = "v";
        /**
        * Parent
        */
        public static final String AttrPar = "par";
        /**
        * Field Base
        */
        public static final String AttrBase = "base";
        /**
        * Source Data Set Beginning Range
        */
        public static final String AttrAutoStart = "autoStart";
        /**
        * Source Data Ending Range
        */
        public static final String AttrAutoEnd = "autoEnd";
        /**
        * Group By
        */
        public static final String AttrGroupBy = "groupBy";
        /**
        * Numeric Grouping Start Value
        */
        public static final String AttrStartNum = "startNum";
        /**
        * Numeric Grouping End Value
        */
        public static final String AttrEndNum = "endNum";
        /**
        * Date Grouping Start Value
        */
        public static final String AttrStartDate = "startDate";
        /**
        * Date Grouping End Value
        */
        public static final String AttrEndDate = "endDate";
        /**
        * Grouping Interval
        */
        public static final String AttrGroupInterval = "groupInterval";
        /**
        * KPI Unique Name
        */
        public static final String AttrUniqueName = "uniqueName";
        /**
        * KPI Display Folder
        */
        public static final String AttrDisplayFolder = "displayFolder";
        /**
        * Parent KPI
        */
        public static final String AttrParent = "parent";
        /**
        * KPI Value Unique Name
        */
        public static final String AttrValue = "value";
        /**
        * KPI Goal Unique Name
        */
        public static final String AttrGoal = "goal";
        /**
        * KPI Status Unique Name
        */
        public static final String AttrStatus = "status";
        /**
        * KPI Trend Unique Name
        */
        public static final String AttrTrend = "trend";
        /**
        * KPI Weight Unique Name
        */
        public static final String AttrWeight = "weight";
        /**
        * Time Member KPI Unique Name
        */
        public static final String AttrTime = "time";
        /**
        * Measure Hierarchy
        */
        public static final String AttrMeasure = "measure";
        /**
        * Parent Set
        */
        public static final String AttrParentSet = "parentSet";
        /**
        * KPI Icon Set
        */
        public static final String AttrIconSet = "iconSet";
        /**
        * Attribute Hierarchy
        */
        public static final String AttrAttribute = "attribute";
        /**
        * Key Attribute Hierarchy
        */
        public static final String AttrKeyAttribute = "keyAttribute";
        /**
        * Default Member Unique Name
        */
        public static final String AttrDefaultMemberUniqueName = "defaultMemberUniqueName";
        /**
        * Unique Name of 'All'
        */
        public static final String AttrAllUniqueName = "allUniqueName";
        /**
        * Display Name of 'All'
        */
        public static final String AttrAllCaption = "allCaption";
        /**
        * Dimension Unique Name
        */
        public static final String AttrDimensionUniqueName = "dimensionUniqueName";
        /**
        * Measures
        */
        public static final String AttrMeasures = "measures";
        /**
        * One Field
        */
        public static final String AttrOneField = "oneField";
        /**
        * Member Value Data Type
        */
        public static final String AttrMemberValueDatatype = "memberValueDatatype";
        /**
        * Unbalanced
        */
        public static final String AttrUnbalanced = "unbalanced";
        /**
        * Unbalanced Group
        */
        public static final String AttrUnbalancedGroup = "unbalancedGroup";
        /**
        * Hidden
        */
        public static final String AttrHidden = "hidden";
        /**
        * User-Defined Group Level
        */
        public static final String AttrUser = "user";
        /**
        * Custom Roll Up
        */
        public static final String AttrCustomRollUp = "customRollUp";
        /**
        * Parent Unique Name
        */
        public static final String AttrUniqueParent = "uniqueParent";
        /**
        * Group Id
        */
        public static final String AttrId = "id";
        /**
        * Culture
        */
        public static final String AttrCulture = "culture";
        /**
        * Field Index
        */
        public static final String AttrFld = "fld";
        /**
        * Hierarchy Index
        */
        public static final String AttrHier = "hier";
        /**
        * Maximum Rank Requested
        */
        public static final String AttrMaxRank = "maxRank";
        /**
        * MDX Set Definition
        */
        public static final String AttrSetDefinition = "setDefinition";
        /**
        * Set Sort Order
        */
        public static final String AttrSortType = "sortType";
        /**
        * Query Failed
        */
        public static final String AttrQueryFailed = "queryFailed";
        /**
        * MDX Query String
        */
        public static final String AttrMdx = "mdx";
        /**
        * OLAP Calculated Member Name
        */
        public static final String AttrMemberName = "memberName";
        /**
        * Calculated Members Solve Order
        */
        public static final String AttrSolveOrder = "solveOrder";
        /**
        * PivotCache Definition Id
        */
        public static final String AttrCacheId = "cacheId";
        /**
        * Data On Rows
        */
        public static final String AttrDataOnRows = "dataOnRows";
        /**
        * Default Data Field Position
        */
        public static final String AttrDataPosition = "dataPosition";
        /**
        * Data Field Header Name
        */
        public static final String AttrDataCaption = "dataCaption";
        /**
        * Grand Totals Caption
        */
        public static final String AttrGrandTotalCaption = "grandTotalCaption";
        /**
        * Error Caption
        */
        public static final String AttrErrorCaption = "errorCaption";
        /**
        * Show Error
        */
        public static final String AttrShowError = "showError";
        /**
        * Caption for Missing Values
        */
        public static final String AttrMissingCaption = "missingCaption";
        /**
        * Show Missing
        */
        public static final String AttrShowMissing = "showMissing";
        /**
        * Page Header Style Name
        */
        public static final String AttrPageStyle = "pageStyle";
        /**
        * Table Style Name
        */
        public static final String AttrPivotTableStyle = "pivotTableStyle";
        /**
        * Vacated Style
        */
        public static final String AttrVacatedStyle = "vacatedStyle";
        /**
        * PivotTable Custom String
        */
        public static final String AttrTag = "tag";
        /**
        * PivotTable Last Updated Version
        */
        public static final String AttrUpdatedVersion = "updatedVersion";
        /**
        * Asterisk Totals
        */
        public static final String AttrAsteriskTotals = "asteriskTotals";
        /**
        * Show Item Names
        */
        public static final String AttrShowItems = "showItems";
        /**
        * Allow Edit Data
        */
        public static final String AttrEditData = "editData";
        /**
        * Disable Field List
        */
        public static final String AttrDisableFieldList = "disableFieldList";
        /**
        * Show Calculated Members
        */
        public static final String AttrShowCalcMbrs = "showCalcMbrs";
        /**
        * Total Visual Data
        */
        public static final String AttrVisualTotals = "visualTotals";
        /**
        * Show Multiple Labels
        */
        public static final String AttrShowMultipleLabel = "showMultipleLabel";
        /**
        * Show Drop Down
        */
        public static final String AttrShowDataDropDown = "showDataDropDown";
        /**
        * Show Expand Collapse
        */
        public static final String AttrShowDrill = "showDrill";
        /**
        * Print Drill Indicators
        */
        public static final String AttrPrintDrill = "printDrill";
        /**
        * Show Member Property ToolTips
        */
        public static final String AttrShowMemberPropertyTips = "showMemberPropertyTips";
        /**
        * Show ToolTips on Data
        */
        public static final String AttrShowDataTips = "showDataTips";
        /**
        * Enable PivotTable Wizard
        */
        public static final String AttrEnableWizard = "enableWizard";
        /**
        * Enable Drill Down
        */
        public static final String AttrEnableDrill = "enableDrill";
        /**
        * Enable Field Properties
        */
        public static final String AttrEnableFieldProperties = "enableFieldProperties";
        /**
        * Preserve Formatting
        */
        public static final String AttrPreserveFormatting = "preserveFormatting";
        /**
        * Auto Formatting
        */
        public static final String AttrUseAutoFormatting = "useAutoFormatting";
        /**
        * Page Wrap
        */
        public static final String AttrPageWrap = "pageWrap";
        /**
        * Page Over Then Down
        */
        public static final String AttrPageOverThenDown = "pageOverThenDown";
        /**
        * Subtotal Hidden Items
        */
        public static final String AttrSubtotalHiddenItems = "subtotalHiddenItems";
        /**
        * Row Grand Totals
        */
        public static final String AttrRowGrandTotals = "rowGrandTotals";
        /**
        * Grand Totals On Columns
        */
        public static final String AttrColGrandTotals = "colGrandTotals";
        /**
        * Field Print Titles
        */
        public static final String AttrFieldPrintTitles = "fieldPrintTitles";
        /**
        * Item Print Titles
        */
        public static final String AttrItemPrintTitles = "itemPrintTitles";
        /**
        * Merge Titles
        */
        public static final String AttrMergeItem = "mergeItem";
        /**
        * Show Drop Zones
        */
        public static final String AttrShowDropZones = "showDropZones";
        /**
        * Indentation for Compact Axis
        */
        public static final String AttrIndent = "indent";
        /**
        * Show Empty Row
        */
        public static final String AttrShowEmptyRow = "showEmptyRow";
        /**
        * Show Empty Column
        */
        public static final String AttrShowEmptyCol = "showEmptyCol";
        /**
        * Show Field Headers
        */
        public static final String AttrShowHeaders = "showHeaders";
        /**
        * Compact New Fields
        */
        public static final String AttrCompact = "compact";
        /**
        * Outline New Fields
        */
        public static final String AttrOutline = "outline";
        /**
        * Outline Data Fields
        */
        public static final String AttrOutlineData = "outlineData";
        /**
        * Compact Data
        */
        public static final String AttrCompactData = "compactData";
        /**
        * Data Fields Published
        */
        public static final String AttrPublished = "published";
        /**
        * Enable Drop Zones
        */
        public static final String AttrGridDropZones = "gridDropZones";
        /**
        * Stop Immersive UI
        */
        public static final String AttrImmersive = "immersive";
        /**
        * Multiple Field Filters
        */
        public static final String AttrMultipleFieldFilters = "multipleFieldFilters";
        /**
        * Row Header Caption
        */
        public static final String AttrRowHeaderCaption = "rowHeaderCaption";
        /**
        * Column Header Caption
        */
        public static final String AttrColHeaderCaption = "colHeaderCaption";
        /**
        * Default Sort Order
        */
        public static final String AttrFieldListSortAscending = "fieldListSortAscending";
        /**
        * MDX Subqueries Supported
        */
        public static final String AttrMdxSubqueries = "mdxSubqueries";
        /**
        * Custom List AutoSort
        */
        public static final String AttrCustomListSort = "customListSort";
        /**
        * First Header Row
        */
        public static final String AttrFirstHeaderRow = "firstHeaderRow";
        /**
        * PivotTable Data First Row
        */
        public static final String AttrFirstDataRow = "firstDataRow";
        /**
        * First Data Column
        */
        public static final String AttrFirstDataCol = "firstDataCol";
        /**
        * Rows Per Page Count
        */
        public static final String AttrRowPageCount = "rowPageCount";
        /**
        * Columns Per Page
        */
        public static final String AttrColPageCount = "colPageCount";
        /**
        * Axis
        */
        public static final String AttrAxis = "axis";
        /**
        * Custom Subtotal Caption
        */
        public static final String AttrSubtotalCaption = "subtotalCaption";
        /**
        * Show PivotField Header Drop Downs
        */
        public static final String AttrShowDropDowns = "showDropDowns";
        /**
        * Hidden Level
        */
        public static final String AttrHiddenLevel = "hiddenLevel";
        /**
        * Unique Member Property
        */
        public static final String AttrUniqueMemberProperty = "uniqueMemberProperty";
        /**
        * All Items Expanded
        */
        public static final String AttrAllDrilled = "allDrilled";
        /**
        * Subtotals At Top
        */
        public static final String AttrSubtotalTop = "subtotalTop";
        /**
        * Drag To Row
        */
        public static final String AttrDragToRow = "dragToRow";
        /**
        * Drag To Column
        */
        public static final String AttrDragToCol = "dragToCol";
        /**
        * Multiple Field Filters
        */
        public static final String AttrMultipleItemSelectionAllowed = "multipleItemSelectionAllowed";
        /**
        * Drag Field to Page
        */
        public static final String AttrDragToPage = "dragToPage";
        /**
        * Field Can Drag to Data
        */
        public static final String AttrDragToData = "dragToData";
        /**
        * Drag Off
        */
        public static final String AttrDragOff = "dragOff";
        /**
        * Show All Items
        */
        public static final String AttrShowAll = "showAll";
        /**
        * Insert Blank Row
        */
        public static final String AttrInsertBlankRow = "insertBlankRow";
        /**
        * Insert Item Page Break
        */
        public static final String AttrInsertPageBreak = "insertPageBreak";
        /**
        * Auto Show
        */
        public static final String AttrAutoShow = "autoShow";
        /**
        * Top Auto Show
        */
        public static final String AttrTopAutoShow = "topAutoShow";
        /**
        * Hide New Items
        */
        public static final String AttrHideNewItems = "hideNewItems";
        /**
        * Measure Filter
        */
        public static final String AttrMeasureFilter = "measureFilter";
        /**
        * Inclusive Manual Filter
        */
        public static final String AttrIncludeNewItemsInFilter = "includeNewItemsInFilter";
        /**
        * Items Per Page Count
        */
        public static final String AttrItemPageCount = "itemPageCount";
        /**
        * Data Source Sort
        */
        public static final String AttrDataSourceSort = "dataSourceSort";
        /**
        * Auto Sort
        */
        public static final String AttrNonAutoSortDefault = "nonAutoSortDefault";
        /**
        * Auto Show Rank By
        */
        public static final String AttrRankBy = "rankBy";
        /**
        * Show Default Subtotal
        */
        public static final String AttrDefaultSubtotal = "defaultSubtotal";
        /**
        * Sum Subtotal
        */
        public static final String AttrSumSubtotal = "sumSubtotal";
        /**
        * CountA
        */
        public static final String AttrCountASubtotal = "countASubtotal";
        /**
        * Average
        */
        public static final String AttrAvgSubtotal = "avgSubtotal";
        /**
        * Max Subtotal
        */
        public static final String AttrMaxSubtotal = "maxSubtotal";
        /**
        * Min Subtotal
        */
        public static final String AttrMinSubtotal = "minSubtotal";
        /**
        * Product Subtotal
        */
        public static final String AttrProductSubtotal = "productSubtotal";
        /**
        * Count
        */
        public static final String AttrCountSubtotal = "countSubtotal";
        /**
        * StdDev Subtotal
        */
        public static final String AttrStdDevSubtotal = "stdDevSubtotal";
        /**
        * StdDevP Subtotal
        */
        public static final String AttrStdDevPSubtotal = "stdDevPSubtotal";
        /**
        * Variance Subtotal
        */
        public static final String AttrVarSubtotal = "varSubtotal";
        /**
        * VarP Subtotal
        */
        public static final String AttrVarPSubtotal = "varPSubtotal";
        /**
        * Show Member Property in Cell
        */
        public static final String AttrShowPropCell = "showPropCell";
        /**
        * Show Member Property ToolTip
        */
        public static final String AttrShowPropTip = "showPropTip";
        /**
        * Show As Caption
        */
        public static final String AttrShowPropAsCaption = "showPropAsCaption";
        /**
        * Drill State
        */
        public static final String AttrDefaultAttributeDrillState = "defaultAttributeDrillState";
        /**
        * Item Type
        */
        public static final String AttrT = "t";
        /**
        * Hidden
        */
        public static final String AttrH = "h";
        /**
        * Hide Details
        */
        public static final String AttrSd = "sd";
        /**
        * Hierarchy Display Name
        */
        public static final String AttrCap = "cap";
        /**
        * Subtotal
        */
        public static final String AttrSubtotal = "subtotal";
        /**
        * Show Data As Display Format
        */
        public static final String AttrShowDataAs = "showDataAs";
        /**
        * 'Show Data As' Base Field
        */
        public static final String AttrBaseField = "baseField";
        /**
        * 'Show Data As' Base Setting
        */
        public static final String AttrBaseItem = "baseItem";
        /**
        * Format Action
        */
        public static final String AttrAction = "action";
        /**
        * Format Id
        */
        public static final String AttrDxfId = "dxfId";
        /**
        * Conditional Formatting Scope
        */
        public static final String AttrScope = "scope";
        /**
        * Priority
        */
        public static final String AttrPriority = "priority";
        /**
        * Chart Index
        */
        public static final String AttrChart = "chart";
        /**
        * Series Format
        */
        public static final String AttrSeries = "series";
        /**
        * Show In Field List
        */
        public static final String AttrShowInFieldList = "showInFieldList";
        /**
        * Hierarchy Usage
        */
        public static final String AttrHierarchyUsage = "hierarchyUsage";
        /**
        * Show Cell
        */
        public static final String AttrShowCell = "showCell";
        /**
        * Show Tooltip
        */
        public static final String AttrShowTip = "showTip";
        /**
        * Show As Caption
        */
        public static final String AttrShowAsCaption = "showAsCaption";
        /**
        * Name Length
        */
        public static final String AttrNameLen = "nameLen";
        /**
        * Property Name Character Index
        */
        public static final String AttrPPos = "pPos";
        /**
        * Property Name Length
        */
        public static final String AttrPLen = "pLen";
        /**
        * Show Row Header Formatting
        */
        public static final String AttrShowRowHeaders = "showRowHeaders";
        /**
        * Show Table Style Column Header Formatting
        */
        public static final String AttrShowColHeaders = "showColHeaders";
        /**
        * Show Row Stripes
        */
        public static final String AttrShowRowStripes = "showRowStripes";
        /**
        * Show Column Stripes
        */
        public static final String AttrShowColStripes = "showColStripes";
        /**
        * Show Last Column
        */
        public static final String AttrShowLastColumn = "showLastColumn";
        /**
        * Member Property Field Id
        */
        public static final String AttrMpFld = "mpFld";
        /**
        * Evaluation Order
        */
        public static final String AttrEvalOrder = "evalOrder";
        /**
        * Measure Index
        */
        public static final String AttrIMeasureHier = "iMeasureHier";
        /**
        * Measure Field Index
        */
        public static final String AttrIMeasureFld = "iMeasureFld";
        /**
        * Pivot Filter Description
        */
        public static final String AttrDescription = "description";
        /**
        * Label Pivot
        */
        public static final String AttrStringValue1 = "stringValue1";
        /**
        * Label Pivot Filter String Value 2
        */
        public static final String AttrStringValue2 = "stringValue2";
    }

    public static class PivotTableShared   
    {
        /**
        * References
        */
        public static final String ElReferences = "references";
        /**
        * Future Feature Data Storage Area
        */
        public static final String ElExtLst = "extLst";
        /**
        * Reference
        */
        public static final String ElReference = "reference";
        /**
        * Field Item
        */
        public static final String ElX = "x";
        /**
        * Field Index
        */
        public static final String AttrField = "field";
        /**
        * Rule Type
        */
        public static final String AttrType = "type";
        /**
        * Data Only
        */
        public static final String AttrDataOnly = "dataOnly";
        /**
        * Labels Only
        */
        public static final String AttrLabelOnly = "labelOnly";
        /**
        * Include Row Grand Total
        */
        public static final String AttrGrandRow = "grandRow";
        /**
        * Include Column Grand Total
        */
        public static final String AttrGrandCol = "grandCol";
        /**
        * Cache Index
        */
        public static final String AttrCacheIndex = "cacheIndex";
        /**
        * Outline
        */
        public static final String AttrOutline = "outline";
        /**
        * Offset Reference
        */
        public static final String AttrOffset = "offset";
        /**
        * Collapsed Levels Are Subtotals
        */
        public static final String AttrCollapsedLevelsAreSubtotals = "collapsedLevelsAreSubtotals";
        /**
        * Axis
        */
        public static final String AttrAxis = "axis";
        /**
        * Field Position
        */
        public static final String AttrFieldPosition = "fieldPosition";
        /**
        * Pivot Filter Count
        */
        public static final String AttrCount = "count";
        /**
        * Selected
        */
        public static final String AttrSelected = "selected";
        /**
        * Positional Reference
        */
        public static final String AttrByPosition = "byPosition";
        /**
        * Relative Reference
        */
        public static final String AttrRelative = "relative";
        /**
        * Include Default Filter
        */
        public static final String AttrDefaultSubtotal = "defaultSubtotal";
        /**
        * Include Sum Filter
        */
        public static final String AttrSumSubtotal = "sumSubtotal";
        /**
        * Include CountA Filter
        */
        public static final String AttrCountASubtotal = "countASubtotal";
        /**
        * Include Average Filter
        */
        public static final String AttrAvgSubtotal = "avgSubtotal";
        /**
        * Include Maximum Filter
        */
        public static final String AttrMaxSubtotal = "maxSubtotal";
        /**
        * Include Minimum Filter
        */
        public static final String AttrMinSubtotal = "minSubtotal";
        /**
        * Include Product Filter
        */
        public static final String AttrProductSubtotal = "productSubtotal";
        /**
        * Include Count Subtotal
        */
        public static final String AttrCountSubtotal = "countSubtotal";
        /**
        * Include StdDev Filter
        */
        public static final String AttrStdDevSubtotal = "stdDevSubtotal";
        /**
        * Include StdDevP Filter
        */
        public static final String AttrStdDevPSubtotal = "stdDevPSubtotal";
        /**
        * Include Var Filter
        */
        public static final String AttrVarSubtotal = "varSubtotal";
        /**
        * Include VarP Filter
        */
        public static final String AttrVarPSubtotal = "varPSubtotal";
        /**
        * Shared Items Index
        */
        public static final String AttrV = "v";
    }

    public static class QueryTable   
    {
        /**
        * Query Table
        */
        public static final String ElQueryTable = "queryTable";
        /**
        * QueryTable Refresh Information
        */
        public static final String ElQueryTableRefresh = "queryTableRefresh";
        /**
        * Future Feature Data Storage Area
        */
        public static final String ElExtLst = "extLst";
        /**
        * Query table fields
        */
        public static final String ElQueryTableFields = "queryTableFields";
        /**
        * Deleted Fields
        */
        public static final String ElQueryTableDeletedFields = "queryTableDeletedFields";
        /**
        * Sort State
        */
        public static final String ElSortState = "sortState";
        /**
        * Deleted Field
        */
        public static final String ElDeletedField = "deletedField";
        /**
        * QueryTable Field
        */
        public static final String ElQueryTableField = "queryTableField";
        /**
        * QueryTable Name
        */
        public static final String AttrName = "name";
        /**
        * First Row Column Titles
        */
        public static final String AttrHeaders = "headers";
        /**
        * Row Numbers
        */
        public static final String AttrRowNumbers = "rowNumbers";
        /**
        * Disable Refresh
        */
        public static final String AttrDisableRefresh = "disableRefresh";
        /**
        * Background Refresh
        */
        public static final String AttrBackgroundRefresh = "backgroundRefresh";
        /**
        * First Background Refresh
        */
        public static final String AttrFirstBackgroundRefresh = "firstBackgroundRefresh";
        /**
        * Refresh On Load
        */
        public static final String AttrRefreshOnLoad = "refreshOnLoad";
        /**
        * Grow Shrink Type
        */
        public static final String AttrGrowShrinkType = "growShrinkType";
        /**
        * Fill Adjacent Formulas
        */
        public static final String AttrFillFormulas = "fillFormulas";
        /**
        * Remove Data On Save
        */
        public static final String AttrRemoveDataOnSave = "removeDataOnSave";
        /**
        * Disable Edit
        */
        public static final String AttrDisableEdit = "disableEdit";
        /**
        * Preserve Formatting On Refresh
        */
        public static final String AttrPreserveFormatting = "preserveFormatting";
        /**
        * Adjust Column Width On Refresh
        */
        public static final String AttrAdjustColumnWidth = "adjustColumnWidth";
        /**
        * Intermediate
        */
        public static final String AttrIntermediate = "intermediate";
        /**
        * Connection Id
        */
        public static final String AttrConnectionId = "connectionId";
        /// <summary>
        /// Preserve Sort & Filter Layout
        /// </summary>
        public static final String AttrPreserveSortFilterLayout = "preserveSortFilterLayout";
        /**
        * Next Field Id Wrapped
        */
        public static final String AttrFieldIdWrapped = "fieldIdWrapped";
        /**
        * Headers In Last Refresh
        */
        public static final String AttrHeadersInLastRefresh = "headersInLastRefresh";
        /**
        * Minimum Refresh Version
        */
        public static final String AttrMinimumVersion = "minimumVersion";
        /**
        * Next field id
        */
        public static final String AttrNextId = "nextId";
        /**
        * Columns Left
        */
        public static final String AttrUnboundColumnsLeft = "unboundColumnsLeft";
        /**
        * Columns Right
        */
        public static final String AttrUnboundColumnsRight = "unboundColumnsRight";
        /**
        * Deleted Fields Count
        */
        public static final String AttrCount = "count";
        /**
        * Field Id
        */
        public static final String AttrId = "id";
        /**
        * Data Bound Column
        */
        public static final String AttrDataBound = "dataBound";
        /**
        * Clipped Column
        */
        public static final String AttrClipped = "clipped";
        /**
        * Table Column Id
        */
        public static final String AttrTableColumnId = "tableColumnId";
    }

    public static class SharedStringTable   
    {
        /**
        * Shared String Table
        */
        public static final String ElSst = "sst";
        /**
        * String Item
        */
        public static final String ElSi = "si";
        /**
        * 
        */
        public static final String ElExtLst = "extLst";
        /**
        * Text
        */
        public static final String ElT = "t";
        /**
        * Run Properties
        */
        public static final String ElRPr = "rPr";
        /**
        * Font
        */
        public static final String ElRFont = "rFont";
        /**
        * Character Set
        */
        public static final String ElCharset = "charset";
        /**
        * Font Family
        */
        public static final String ElFamily = "family";
        /**
        * Bold
        */
        public static final String ElB = "b";
        /**
        * Italic
        */
        public static final String ElI = "i";
        /**
        * Strike Through
        */
        public static final String ElStrike = "strike";
        /**
        * Outline
        */
        public static final String ElOutline = "outline";
        /**
        * Shadow
        */
        public static final String ElShadow = "shadow";
        /**
        * Condense
        */
        public static final String ElCondense = "condense";
        /**
        * Extend
        */
        public static final String ElExtend = "extend";
        /**
        * Text Color
        */
        public static final String ElColor = "color";
        /**
        * Font Size
        */
        public static final String ElSz = "sz";
        /**
        * Underline
        */
        public static final String ElU = "u";
        /**
        * Vertical Alignment
        */
        public static final String ElVertAlign = "vertAlign";
        /**
        * Font Scheme
        */
        public static final String ElScheme = "scheme";
        /**
        * Rich Text Run
        */
        public static final String ElR = "r";
        /**
        * Phonetic Run
        */
        public static final String ElRPh = "rPh";
        /**
        * Phonetic Properties
        */
        public static final String ElPhoneticPr = "phoneticPr";
        /**
        * String Count
        */
        public static final String AttrCount = "count";
        /**
        * Unique String Count
        */
        public static final String AttrUniqueCount = "uniqueCount";
        /**
        * Base Text Start Index
        */
        public static final String AttrSb = "sb";
        /**
        * Base Text End Index
        */
        public static final String AttrEb = "eb";
        /**
        * Font Id
        */
        public static final String AttrFontId = "fontId";
        /**
        * Character Type
        */
        public static final String AttrType = "type";
        /**
        * Alignment
        */
        public static final String AttrAlignment = "alignment";
    }

    public static class SharedWorkbookRevisions   
    {
        /**
        * Revision Headers
        */
        public static final String ElHeaders = "headers";
        /**
        * Revisions
        */
        public static final String ElRevisions = "revisions";
        /**
        * Header
        */
        public static final String ElHeader = "header";
        /**
        * Revision Row Column Insert Delete
        */
        public static final String ElRrc = "rrc";
        /**
        * Revision Cell Move
        */
        public static final String ElRm = "rm";
        /**
        * Revision Custom View
        */
        public static final String ElRcv = "rcv";
        /**
        * Revision Sheet Name
        */
        public static final String ElRsnm = "rsnm";
        /**
        * Revision Insert Sheet
        */
        public static final String ElRis = "ris";
        /**
        * Revision Cell Change
        */
        public static final String ElRcc = "rcc";
        /**
        * Revision Format
        */
        public static final String ElRfmt = "rfmt";
        /**
        * Revision AutoFormat
        */
        public static final String ElRaf = "raf";
        /**
        * Revision Defined Name
        */
        public static final String ElRdn = "rdn";
        /**
        * Revision Cell Comment
        */
        public static final String ElRcmt = "rcmt";
        /**
        * Revision Query Table
        */
        public static final String ElRqt = "rqt";
        /**
        * Revision Merge Conflict
        */
        public static final String ElRcft = "rcft";
        /**
        * Sheet Id Map
        */
        public static final String ElSheetIdMap = "sheetIdMap";
        /**
        * Reviewed List
        */
        public static final String ElReviewedList = "reviewedList";
        /**
        * 
        */
        public static final String ElExtLst = "extLst";
        /**
        * Sheet Id
        */
        public static final String ElSheetId = "sheetId";
        /**
        * Reviewed
        */
        public static final String ElReviewed = "reviewed";
        /**
        * Undo
        */
        public static final String ElUndo = "undo";
        /**
        * Old Cell Data
        */
        public static final String ElOc = "oc";
        /**
        * New Cell Data
        */
        public static final String ElNc = "nc";
        /**
        * Old Formatting Information
        */
        public static final String ElOdxf = "odxf";
        /**
        * New Formatting Information
        */
        public static final String ElNdxf = "ndxf";
        /**
        * Formatting
        */
        public static final String ElDxf = "dxf";
        /**
        * Formula
        */
        public static final String ElFormula = "formula";
        /**
        * Old Formula
        */
        public static final String ElOldFormula = "oldFormula";
        /**
        * Last Revision GUID
        */
        public static final String AttrGuid = "guid";
        /**
        * Last GUID
        */
        public static final String AttrLastGuid = "lastGuid";
        /**
        * Shared Workbook
        */
        public static final String AttrShared = "shared";
        /**
        * Disk Revisions
        */
        public static final String AttrDiskRevisions = "diskRevisions";
        /**
        * History
        */
        public static final String AttrHistory = "history";
        /**
        * Track Revisions
        */
        public static final String AttrTrackRevisions = "trackRevisions";
        /**
        * Exclusive Mode
        */
        public static final String AttrExclusive = "exclusive";
        /**
        * Revision Id
        */
        public static final String AttrRevisionId = "revisionId";
        /**
        * Version
        */
        public static final String AttrVersion = "version";
        /**
        * Keep Change History
        */
        public static final String AttrKeepChangeHistory = "keepChangeHistory";
        /**
        * Protected
        */
        public static final String AttrProtected = "protected";
        /**
        * Preserve History
        */
        public static final String AttrPreserveHistory = "preserveHistory";
        /**
        * Revision Id
        */
        public static final String AttrRId = "rId";
        /**
        * Revision From Rejection
        */
        public static final String AttrUa = "ua";
        /**
        * Revision Undo Rejected
        */
        public static final String AttrRa = "ra";
        /**
        * Date Time
        */
        public static final String AttrDateTime = "dateTime";
        /**
        * Last Sheet Id
        */
        public static final String AttrMaxSheetId = "maxSheetId";
        /**
        * User Name
        */
        public static final String AttrUserName = "userName";
        /**
        * Minimum Revision Id
        */
        public static final String AttrMinRId = "minRId";
        /**
        * Max Revision Id
        */
        public static final String AttrMaxRId = "maxRId";
        /**
        * Sheet Count
        */
        public static final String AttrCount = "count";
        /**
        * Sheet Id
        */
        public static final String AttrVal = "val";
        /**
        * Index
        */
        public static final String AttrIndex = "index";
        /**
        * Expression
        */
        public static final String AttrExp = "exp";
        /**
        * Reference 3D
        */
        public static final String AttrRef3D = "ref3D";
        /**
        * Array Entered
        */
        public static final String AttrArray = "array";
        /**
        * Value Needed
        */
        public static final String AttrV = "v";
        /**
        * Defined Name Formula
        */
        public static final String AttrNf = "nf";
        /**
        * Cross Sheet Move
        */
        public static final String AttrCs = "cs";
        /**
        * Range
        */
        public static final String AttrDr = "dr";
        /**
        * Defined Name
        */
        public static final String AttrDn = "dn";
        /**
        * Cell Reference
        */
        public static final String AttrR = "r";
        /**
        * Sheet Id
        */
        public static final String AttrSId = "sId";
        /**
        * End Of List
        */
        public static final String AttrEol = "eol";
        /**
        * Reference
        */
        public static final String AttrRef = "ref";
        /**
        * User Action
        */
        public static final String AttrAction = "action";
        /**
        * Edge Deleted
        */
        public static final String AttrEdge = "edge";
        /**
        * Source
        */
        public static final String AttrSource = "source";
        /**
        * Destination
        */
        public static final String AttrDestination = "destination";
        /**
        * Source Sheet Id
        */
        public static final String AttrSourceSheetId = "sourceSheetId";
        /**
        * Old Sheet Name
        */
        public static final String AttrOldName = "oldName";
        /**
        * New Sheet Name
        */
        public static final String AttrNewName = "newName";
        /**
        * Sheet Name
        */
        public static final String AttrName = "name";
        /**
        * Sheet Position
        */
        public static final String AttrSheetPosition = "sheetPosition";
        /**
        * Row Column Formatting Change
        */
        public static final String AttrXfDxf = "xfDxf";
        /**
        * Style Revision
        */
        public static final String AttrS = "s";
        /**
        * Number Format Id
        */
        public static final String AttrNumFmtId = "numFmtId";
        /**
        * Quote Prefix
        */
        public static final String AttrQuotePrefix = "quotePrefix";
        /**
        * Old Quote Prefix
        */
        public static final String AttrOldQuotePrefix = "oldQuotePrefix";
        /**
        * Phonetic Text
        */
        public static final String AttrPh = "ph";
        /**
        * Old Phonetic Text
        */
        public static final String AttrOldPh = "oldPh";
        /**
        * End of List  Formula Update
        */
        public static final String AttrEndOfListFormulaUpdate = "endOfListFormulaUpdate";
        /**
        * Sequence Of References
        */
        public static final String AttrSqref = "sqref";
        /**
        * Start index
        */
        public static final String AttrStart = "start";
        /**
        * Length
        */
        public static final String AttrLength = "length";
        /**
        * Cell
        */
        public static final String AttrCell = "cell";
        /**
        * Always Show Comment
        */
        public static final String AttrAlwaysShow = "alwaysShow";
        /**
        * Old Comment
        */
        public static final String AttrOld = "old";
        /**
        * Comment In Hidden Row
        */
        public static final String AttrHiddenRow = "hiddenRow";
        /**
        * Hidden Column
        */
        public static final String AttrHiddenColumn = "hiddenColumn";
        /**
        * Author
        */
        public static final String AttrAuthor = "author";
        /**
        * Original Comment Length
        */
        public static final String AttrOldLength = "oldLength";
        /**
        * New Comment Length
        */
        public static final String AttrNewLength = "newLength";
        /**
        * Local Name Sheet Id
        */
        public static final String AttrLocalSheetId = "localSheetId";
        /**
        * Custom View
        */
        public static final String AttrCustomView = "customView";
        /**
        * Function
        */
        public static final String AttrFunction = "function";
        /**
        * Old Function
        */
        public static final String AttrOldFunction = "oldFunction";
        /**
        * Function Group Id
        */
        public static final String AttrFunctionGroupId = "functionGroupId";
        /**
        * Old Function Group Id
        */
        public static final String AttrOldFunctionGroupId = "oldFunctionGroupId";
        /**
        * Shortcut Key
        */
        public static final String AttrShortcutKey = "shortcutKey";
        /**
        * Old Short Cut Key
        */
        public static final String AttrOldShortcutKey = "oldShortcutKey";
        /**
        * Named Range Hidden
        */
        public static final String AttrHidden = "hidden";
        /**
        * Old Hidden
        */
        public static final String AttrOldHidden = "oldHidden";
        /**
        * New Custom Menu
        */
        public static final String AttrCustomMenu = "customMenu";
        /**
        * Old Custom Menu Text
        */
        public static final String AttrOldCustomMenu = "oldCustomMenu";
        /**
        * Description
        */
        public static final String AttrDescription = "description";
        /**
        * Old Description
        */
        public static final String AttrOldDescription = "oldDescription";
        /**
        * New Help Topic
        */
        public static final String AttrHelp = "help";
        /**
        * Old Help Topic
        */
        public static final String AttrOldHelp = "oldHelp";
        /**
        * Status Bar
        */
        public static final String AttrStatusBar = "statusBar";
        /**
        * Old Status Bar
        */
        public static final String AttrOldStatusBar = "oldStatusBar";
        /**
        * Name Comment
        */
        public static final String AttrComment = "comment";
        /**
        * Old Name Comment
        */
        public static final String AttrOldComment = "oldComment";
        /**
        * Field Id
        */
        public static final String AttrFieldId = "fieldId";
    }

    public static class SharedWorkbookUserNames   
    {
        /**
        * User List
        */
        public static final String ElUsers = "users";
        /**
        * User Information
        */
        public static final String ElUserInfo = "userInfo";
        /**
        * 
        */
        public static final String ElExtLst = "extLst";
        /**
        * Active User Count
        */
        public static final String AttrCount = "count";
        /**
        * User Revisions GUID
        */
        public static final String AttrGuid = "guid";
        /**
        * User Name
        */
        public static final String AttrName = "name";
        /**
        * User Id
        */
        public static final String AttrId = "id";
        /**
        * Date Time
        */
        public static final String AttrDateTime = "dateTime";
    }

    public static class Sheet   
    {
        /**
        * Worksheet
        */
        public static final String ElWorksheet = "worksheet";
        /**
        * Chart Sheet
        */
        public static final String ElChartsheet = "chartsheet";
        /**
        * Dialog Sheet
        */
        public static final String ElDialogsheet = "dialogsheet";
        /**
        * Sheet Properties
        */
        public static final String ElSheetPr = "sheetPr";
        /**
        * Macro Sheet Dimensions
        */
        public static final String ElDimension = "dimension";
        /**
        * Macro Sheet Views
        */
        public static final String ElSheetViews = "sheetViews";
        /**
        * Sheet Format Properties
        */
        public static final String ElSheetFormatPr = "sheetFormatPr";
        /**
        * Column Information
        */
        public static final String ElCols = "cols";
        /**
        * Sheet Data
        */
        public static final String ElSheetData = "sheetData";
        /**
        * Sheet Protection Options
        */
        public static final String ElSheetProtection = "sheetProtection";
        /**
        * AutoFilter
        */
        public static final String ElAutoFilter = "autoFilter";
        /**
        * Sort State
        */
        public static final String ElSortState = "sortState";
        /**
        * Data Consolidation
        */
        public static final String ElDataConsolidate = "dataConsolidate";
        /**
        * Custom Sheet Views
        */
        public static final String ElCustomSheetViews = "customSheetViews";
        /**
        * Phonetic Properties
        */
        public static final String ElPhoneticPr = "phoneticPr";
        /**
        * Conditional Formatting
        */
        public static final String ElConditionalFormatting = "conditionalFormatting";
        /**
        * Print Options
        */
        public static final String ElPrintOptions = "printOptions";
        /**
        * Page Margins
        */
        public static final String ElPageMargins = "pageMargins";
        /**
        * Page Setup Settings
        */
        public static final String ElPageSetup = "pageSetup";
        /**
        * Header Footer Settings
        */
        public static final String ElHeaderFooter = "headerFooter";
        /**
        * Horizontal Page Breaks (Row)
        */
        public static final String ElRowBreaks = "rowBreaks";
        /**
        * Vertical Page Breaks
        */
        public static final String ElColBreaks = "colBreaks";
        /**
        * Custom Properties
        */
        public static final String ElCustomProperties = "customProperties";
        /**
        * Drawing
        */
        public static final String ElDrawing = "drawing";
        /**
        * Legacy Drawing Reference
        */
        public static final String ElLegacyDrawing = "legacyDrawing";
        /**
        * Legacy Drawing Header Footer
        */
        public static final String ElLegacyDrawingHF = "legacyDrawingHF";
        /**
        * Background Image
        */
        public static final String ElPicture = "picture";
        /**
        * OLE Objects
        */
        public static final String ElOleObjects = "oleObjects";
        /**
        * Future Feature Data Storage Area
        */
        public static final String ElExtLst = "extLst";
        /**
        * Sheet Calculation Properties
        */
        public static final String ElSheetCalcPr = "sheetCalcPr";
        /**
        * Protected Ranges
        */
        public static final String ElProtectedRanges = "protectedRanges";
        /**
        * Scenarios
        */
        public static final String ElScenarios = "scenarios";
        /**
        * Merge Cells
        */
        public static final String ElMergeCells = "mergeCells";
        /**
        * Data Validations
        */
        public static final String ElDataValidations = "dataValidations";
        /**
        * Hyperlinks
        */
        public static final String ElHyperlinks = "hyperlinks";
        /**
        * Cell Watch Items
        */
        public static final String ElCellWatches = "cellWatches";
        /**
        * Ignored Errors
        */
        public static final String ElIgnoredErrors = "ignoredErrors";
        /**
        * Smart Tags
        */
        public static final String ElSmartTags = "smartTags";
        /**
        * Embedded Controls
        */
        public static final String ElControls = "controls";
        /**
        * Web Publishing Items
        */
        public static final String ElWebPublishItems = "webPublishItems";
        /**
        * Table Parts
        */
        public static final String ElTableParts = "tableParts";
        /**
        * Row
        */
        public static final String ElRow = "row";
        /// <summary>
        /// Column Width & Formatting
        /// </summary>
        public static final String ElCol = "col";
        /**
        * Cell
        */
        public static final String ElC = "c";
        /**
        * Formula
        */
        public static final String ElF = "f";
        /**
        * Cell Value
        */
        public static final String ElV = "v";
        /**
        * Rich Text Inline
        */
        public static final String ElIs = "is";
        /**
        * Sheet Tab Color
        */
        public static final String ElTabColor = "tabColor";
        /**
        * Outline Properties
        */
        public static final String ElOutlinePr = "outlinePr";
        /**
        * Page Setup Properties
        */
        public static final String ElPageSetUpPr = "pageSetUpPr";
        /**
        * Worksheet View
        */
        public static final String ElSheetView = "sheetView";
        /**
        * View Pane
        */
        public static final String ElPane = "pane";
        /**
        * Selection
        */
        public static final String ElSelection = "selection";
        /**
        * PivotTable Selection
        */
        public static final String ElPivotSelection = "pivotSelection";
        /**
        * Pivot Area
        */
        public static final String ElPivotArea = "pivotArea";
        /**
        * Break
        */
        public static final String ElBrk = "brk";
        /**
        * Data Consolidation References
        */
        public static final String ElDataRefs = "dataRefs";
        /**
        * Data Consolidation Reference
        */
        public static final String ElDataRef = "dataRef";
        /**
        * Merged Cell
        */
        public static final String ElMergeCell = "mergeCell";
        /**
        * Cell Smart Tags
        */
        public static final String ElCellSmartTags = "cellSmartTags";
        /**
        * Cell Smart Tag
        */
        public static final String ElCellSmartTag = "cellSmartTag";
        /**
        * Smart Tag Properties
        */
        public static final String ElCellSmartTagPr = "cellSmartTagPr";
        /**
        * Custom Sheet View
        */
        public static final String ElCustomSheetView = "customSheetView";
        /**
        * Data Validation
        */
        public static final String ElDataValidation = "dataValidation";
        /**
        * Formula 1
        */
        public static final String ElFormula1 = "formula1";
        /**
        * Formula 2
        */
        public static final String ElFormula2 = "formula2";
        /**
        * Conditional Formatting Rule
        */
        public static final String ElCfRule = "cfRule";
        /**
        * Formula
        */
        public static final String ElFormula = "formula";
        /**
        * Color Scale
        */
        public static final String ElColorScale = "colorScale";
        /**
        * Data Bar
        */
        public static final String ElDataBar = "dataBar";
        /**
        * Icon Set
        */
        public static final String ElIconSet = "iconSet";
        /**
        * Hyperlink
        */
        public static final String ElHyperlink = "hyperlink";
        /**
        * Conditional Format Value Object
        */
        public static final String ElCfvo = "cfvo";
        /**
        * Color Gradiant Interpolation
        */
        public static final String ElColor = "color";
        /**
        * Odd Header
        */
        public static final String ElOddHeader = "oddHeader";
        /**
        * Odd Page Footer
        */
        public static final String ElOddFooter = "oddFooter";
        /**
        * Even Page Header
        */
        public static final String ElEvenHeader = "evenHeader";
        /**
        * Even Page Footer
        */
        public static final String ElEvenFooter = "evenFooter";
        /**
        * First Page Header
        */
        public static final String ElFirstHeader = "firstHeader";
        /**
        * First Page Footer
        */
        public static final String ElFirstFooter = "firstFooter";
        /**
        * Scenario
        */
        public static final String ElScenario = "scenario";
        /**
        * Protected Range
        */
        public static final String ElProtectedRange = "protectedRange";
        /**
        * Input Cells
        */
        public static final String ElInputCells = "inputCells";
        /**
        * Cell Watch Item
        */
        public static final String ElCellWatch = "cellWatch";
        /**
        * Custom Property
        */
        public static final String ElCustomPr = "customPr";
        /**
        * OLE Object
        */
        public static final String ElOleObject = "oleObject";
        /**
        * Web Publishing Item
        */
        public static final String ElWebPublishItem = "webPublishItem";
        /**
        * Embedded Control
        */
        public static final String ElControl = "control";
        /**
        * Ignored Error
        */
        public static final String ElIgnoredError = "ignoredError";
        /**
        * Table Part
        */
        public static final String ElTablePart = "tablePart";
        /**
        * Full Calculation On Load
        */
        public static final String AttrFullCalcOnLoad = "fullCalcOnLoad";
        /**
        * Base Column Width
        */
        public static final String AttrBaseColWidth = "baseColWidth";
        /**
        * Default Column Width
        */
        public static final String AttrDefaultColWidth = "defaultColWidth";
        /**
        * Default Row Height
        */
        public static final String AttrDefaultRowHeight = "defaultRowHeight";
        /**
        * Custom Height
        */
        public static final String AttrCustomHeight = "customHeight";
        /**
        * Hidden By Default
        */
        public static final String AttrZeroHeight = "zeroHeight";
        /**
        * Thick Top Border
        */
        public static final String AttrThickTop = "thickTop";
        /**
        * Thick Bottom Border
        */
        public static final String AttrThickBottom = "thickBottom";
        /**
        * Maximum Outline Row
        */
        public static final String AttrOutlineLevelRow = "outlineLevelRow";
        /**
        * Column Outline Level
        */
        public static final String AttrOutlineLevelCol = "outlineLevelCol";
        /**
        * Minimum Column
        */
        public static final String AttrMin = "min";
        /**
        * Maximum Column
        */
        public static final String AttrMax = "max";
        /**
        * Column Width
        */
        public static final String AttrWidth = "width";
        /**
        * Style
        */
        public static final String AttrStyle = "style";
        /**
        * Hidden Columns
        */
        public static final String AttrHidden = "hidden";
        /**
        * Best Fit Column Width
        */
        public static final String AttrBestFit = "bestFit";
        /**
        * Custom Width
        */
        public static final String AttrCustomWidth = "customWidth";
        /**
        * Show Phonetic Information
        */
        public static final String AttrPhonetic = "phonetic";
        /**
        * Outline Level
        */
        public static final String AttrOutlineLevel = "outlineLevel";
        /**
        * Collapsed
        */
        public static final String AttrCollapsed = "collapsed";
        /**
        * Row Index
        */
        public static final String AttrR = "r";
        /**
        * Spans
        */
        public static final String AttrSpans = "spans";
        /**
        * Style Index
        */
        public static final String AttrS = "s";
        /**
        * Custom Format
        */
        public static final String AttrCustomFormat = "customFormat";
        /**
        * Row Height
        */
        public static final String AttrHt = "ht";
        /**
        * Thick Bottom
        */
        public static final String AttrThickBot = "thickBot";
        /**
        * Show Phonetic
        */
        public static final String AttrPh = "ph";
        /**
        * Cell Data Type
        */
        public static final String AttrT = "t";
        /**
        * Cell Metadata Index
        */
        public static final String AttrCm = "cm";
        /**
        * Value Metadata Index
        */
        public static final String AttrVm = "vm";
        /**
        * Synch Horizontal
        */
        public static final String AttrSyncHorizontal = "syncHorizontal";
        /**
        * Synch Vertical
        */
        public static final String AttrSyncVertical = "syncVertical";
        /**
        * Synch Reference
        */
        public static final String AttrSyncRef = "syncRef";
        /**
        * Transition Formula Evaluation
        */
        public static final String AttrTransitionEvaluation = "transitionEvaluation";
        /**
        * Transition Formula Entry
        */
        public static final String AttrTransitionEntry = "transitionEntry";
        /**
        * Published
        */
        public static final String AttrPublished = "published";
        /**
        * Code Name
        */
        public static final String AttrCodeName = "codeName";
        /**
        * Filter Mode
        */
        public static final String AttrFilterMode = "filterMode";
        /**
        * Enable Conditional Formatting Calculations
        */
        public static final String AttrEnableFormatConditionsCalculation = "enableFormatConditionsCalculation";
        /**
        * Reference
        */
        public static final String AttrRef = "ref";
        /**
        * Window Protection
        */
        public static final String AttrWindowProtection = "windowProtection";
        /**
        * Show Formulas
        */
        public static final String AttrShowFormulas = "showFormulas";
        /**
        * Show Grid Lines
        */
        public static final String AttrShowGridLines = "showGridLines";
        /**
        * Show Headers
        */
        public static final String AttrShowRowColHeaders = "showRowColHeaders";
        /**
        * Show Zero Values
        */
        public static final String AttrShowZeros = "showZeros";
        /**
        * Right To Left
        */
        public static final String AttrRightToLeft = "rightToLeft";
        /**
        * Sheet Tab Selected
        */
        public static final String AttrTabSelected = "tabSelected";
        /**
        * Show Ruler
        */
        public static final String AttrShowRuler = "showRuler";
        /**
        * Show Outline Symbols
        */
        public static final String AttrShowOutlineSymbols = "showOutlineSymbols";
        /**
        * Default Grid Color
        */
        public static final String AttrDefaultGridColor = "defaultGridColor";
        /**
        * Show White Space
        */
        public static final String AttrShowWhiteSpace = "showWhiteSpace";
        /**
        * View Type
        */
        public static final String AttrView = "view";
        /**
        * Top Left Visible Cell
        */
        public static final String AttrTopLeftCell = "topLeftCell";
        /**
        * Color Id
        */
        public static final String AttrColorId = "colorId";
        /**
        * Zoom Scale
        */
        public static final String AttrZoomScale = "zoomScale";
        /**
        * Zoom Scale Normal View
        */
        public static final String AttrZoomScaleNormal = "zoomScaleNormal";
        /**
        * Zoom Scale Page Break Preview
        */
        public static final String AttrZoomScaleSheetLayoutView = "zoomScaleSheetLayoutView";
        /**
        * Zoom Scale Page Layout View
        */
        public static final String AttrZoomScalePageLayoutView = "zoomScalePageLayoutView";
        /**
        * Workbook View Index
        */
        public static final String AttrWorkbookViewId = "workbookViewId";
        /**
        * Horizontal Split Position
        */
        public static final String AttrXSplit = "xSplit";
        /**
        * Vertical Split Position
        */
        public static final String AttrYSplit = "ySplit";
        /**
        * Active Pane
        */
        public static final String AttrActivePane = "activePane";
        /**
        * Split State
        */
        public static final String AttrState = "state";
        /**
        * Show Header
        */
        public static final String AttrShowHeader = "showHeader";
        /**
        * Label
        */
        public static final String AttrLabel = "label";
        /**
        * Data Selection
        */
        public static final String AttrData = "data";
        /**
        * Extendable
        */
        public static final String AttrExtendable = "extendable";
        /**
        * Selection Count
        */
        public static final String AttrCount = "count";
        /**
        * Axis
        */
        public static final String AttrAxis = "axis";
        /**
        * Start
        */
        public static final String AttrStart = "start";
        /**
        * Active Row
        */
        public static final String AttrActiveRow = "activeRow";
        /**
        * Active Column
        */
        public static final String AttrActiveCol = "activeCol";
        /**
        * Previous Row
        */
        public static final String AttrPreviousRow = "previousRow";
        /**
        * Previous Column Selection
        */
        public static final String AttrPreviousCol = "previousCol";
        /**
        * Click Count
        */
        public static final String AttrClick = "click";
        /**
        * Active Cell Location
        */
        public static final String AttrActiveCell = "activeCell";
        /**
        * Active Cell Index
        */
        public static final String AttrActiveCellId = "activeCellId";
        /**
        * Sequence of References
        */
        public static final String AttrSqref = "sqref";
        /**
        * Manual Break Count
        */
        public static final String AttrManualBreakCount = "manualBreakCount";
        /**
        * Id
        */
        public static final String AttrId = "id";
        /**
        * Manual Page Break
        */
        public static final String AttrMan = "man";
        /**
        * Pivot-Created Page Break
        */
        public static final String AttrPt = "pt";
        /**
        * Apply Styles in Outline
        */
        public static final String AttrApplyStyles = "applyStyles";
        /**
        * Summary Below
        */
        public static final String AttrSummaryBelow = "summaryBelow";
        /**
        * Summary Right
        */
        public static final String AttrSummaryRight = "summaryRight";
        /**
        * Show Auto Page Breaks
        */
        public static final String AttrAutoPageBreaks = "autoPageBreaks";
        /**
        * Fit To Page
        */
        public static final String AttrFitToPage = "fitToPage";
        /**
        * Function Index
        */
        public static final String AttrFunction = "function";
        /**
        * Use Left Column Labels
        */
        public static final String AttrLeftLabels = "leftLabels";
        /**
        * Labels In Top Row
        */
        public static final String AttrTopLabels = "topLabels";
        /**
        * Link
        */
        public static final String AttrLink = "link";
        /**
        * Named Range
        */
        public static final String AttrName = "name";
        /**
        * Sheet Name
        */
        public static final String AttrSheet = "sheet";
        /**
        * Smart Tag Type Index
        */
        public static final String AttrType = "type";
        /**
        * Deleted
        */
        public static final String AttrDeleted = "deleted";
        /**
        * XML Based
        */
        public static final String AttrXmlBased = "xmlBased";
        /**
        * Key Name
        */
        public static final String AttrKey = "key";
        /**
        * Value
        */
        public static final String AttrVal = "val";
        /**
        * GUID
        */
        public static final String AttrGuid = "guid";
        /**
        * Print Scale
        */
        public static final String AttrScale = "scale";
        /**
        * Show Page Breaks
        */
        public static final String AttrShowPageBreaks = "showPageBreaks";
        /**
        * Show Headers
        */
        public static final String AttrShowRowCol = "showRowCol";
        /**
        * Show Outline Symbols
        */
        public static final String AttrOutlineSymbols = "outlineSymbols";
        /**
        * Show Zero Values
        */
        public static final String AttrZeroValues = "zeroValues";
        /**
        * Print Area Defined
        */
        public static final String AttrPrintArea = "printArea";
        /**
        * Filtered List
        */
        public static final String AttrFilter = "filter";
        /**
        * Show AutoFitler Drop Down Controls
        */
        public static final String AttrShowAutoFilter = "showAutoFilter";
        /**
        * Hidden Rows
        */
        public static final String AttrHiddenRows = "hiddenRows";
        /**
        * Hidden Columns
        */
        public static final String AttrHiddenColumns = "hiddenColumns";
        /**
        * Filter
        */
        public static final String AttrFilterUnique = "filterUnique";
        /**
        * Disable Prompts
        */
        public static final String AttrDisablePrompts = "disablePrompts";
        /**
        * Top Left Corner (X Coodrinate)
        */
        public static final String AttrXWindow = "xWindow";
        /**
        * Top Left Corner (Y Coordinate)
        */
        public static final String AttrYWindow = "yWindow";
        /**
        * Data Validation Error Style
        */
        public static final String AttrErrorStyle = "errorStyle";
        /**
        * IME Mode Enforced
        */
        public static final String AttrImeMode = "imeMode";
        /**
        * Operator
        */
        public static final String AttrOperator = "operator";
        /**
        * Allow Blank
        */
        public static final String AttrAllowBlank = "allowBlank";
        /**
        * Show Drop Down
        */
        public static final String AttrShowDropDown = "showDropDown";
        /**
        * Show Input Message
        */
        public static final String AttrShowInputMessage = "showInputMessage";
        /**
        * Show Error Message
        */
        public static final String AttrShowErrorMessage = "showErrorMessage";
        /**
        * Error Alert Text
        */
        public static final String AttrErrorTitle = "errorTitle";
        /**
        * Error Message
        */
        public static final String AttrError = "error";
        /**
        * Prompt Title
        */
        public static final String AttrPromptTitle = "promptTitle";
        /**
        * Input Prompt
        */
        public static final String AttrPrompt = "prompt";
        /**
        * PivotTable Conditional Formatting
        */
        public static final String AttrPivot = "pivot";
        /**
        * Differential Formatting Id
        */
        public static final String AttrDxfId = "dxfId";
        /**
        * Priority
        */
        public static final String AttrPriority = "priority";
        /**
        * Stop If True
        */
        public static final String AttrStopIfTrue = "stopIfTrue";
        /**
        * Above Or Below Average
        */
        public static final String AttrAboveAverage = "aboveAverage";
        /**
        * Top 10 Percent
        */
        public static final String AttrPercent = "percent";
        /**
        * Bottom N
        */
        public static final String AttrBottom = "bottom";
        /**
        * Text
        */
        public static final String AttrText = "text";
        /**
        * Time Period
        */
        public static final String AttrTimePeriod = "timePeriod";
        /**
        * Rank
        */
        public static final String AttrRank = "rank";
        /**
        * StdDev
        */
        public static final String AttrStdDev = "stdDev";
        /**
        * Equal Average
        */
        public static final String AttrEqualAverage = "equalAverage";
        /**
        * Location
        */
        public static final String AttrLocation = "location";
        /**
        * Tool Tip
        */
        public static final String AttrTooltip = "tooltip";
        /**
        * Display String
        */
        public static final String AttrDisplay = "display";
        /**
        * Always Calculate Array
        */
        public static final String AttrAca = "aca";
        /**
        * Data Table 2-D
        */
        public static final String AttrDt2D = "dt2D";
        /**
        * Data Table Row
        */
        public static final String AttrDtr = "dtr";
        /**
        * Input 1 Deleted
        */
        public static final String AttrDel1 = "del1";
        /**
        * Input 2 Deleted
        */
        public static final String AttrDel2 = "del2";
        /**
        * Data Table Cell 1
        */
        public static final String AttrR1 = "r1";
        /**
        * Input Cell 2
        */
        public static final String AttrR2 = "r2";
        /**
        * Calculate Cell
        */
        public static final String AttrCa = "ca";
        /**
        * Shared Group Index
        */
        public static final String AttrSi = "si";
        /**
        * Assigns Value to Name
        */
        public static final String AttrBx = "bx";
        /**
        * Minimum Length
        */
        public static final String AttrMinLength = "minLength";
        /**
        * Maximum Length
        */
        public static final String AttrMaxLength = "maxLength";
        /**
        * Show Values
        */
        public static final String AttrShowValue = "showValue";
        /**
        * Reverse Icons
        */
        public static final String AttrReverse = "reverse";
        /**
        * Greater Than Or Equal
        */
        public static final String AttrGte = "gte";
        /**
        * Left Page Margin
        */
        public static final String AttrLeft = "left";
        /**
        * Right Page Margin
        */
        public static final String AttrRight = "right";
        /**
        * Top Page Margin
        */
        public static final String AttrTop = "top";
        /**
        * Header Page Margin
        */
        public static final String AttrHeader = "header";
        /**
        * Footer Page Margin
        */
        public static final String AttrFooter = "footer";
        /**
        * Horizontal Centered
        */
        public static final String AttrHorizontalCentered = "horizontalCentered";
        /**
        * Vertical Centered
        */
        public static final String AttrVerticalCentered = "verticalCentered";
        /**
        * Print Headings
        */
        public static final String AttrHeadings = "headings";
        /**
        * Print Grid Lines
        */
        public static final String AttrGridLines = "gridLines";
        /**
        * Grid Lines Set
        */
        public static final String AttrGridLinesSet = "gridLinesSet";
        /**
        * Paper Size
        */
        public static final String AttrPaperSize = "paperSize";
        /**
        * First Page Number
        */
        public static final String AttrFirstPageNumber = "firstPageNumber";
        /**
        * Fit To Width
        */
        public static final String AttrFitToWidth = "fitToWidth";
        /**
        * Fit To Height
        */
        public static final String AttrFitToHeight = "fitToHeight";
        /**
        * Page Order
        */
        public static final String AttrPageOrder = "pageOrder";
        /**
        * Orientation
        */
        public static final String AttrOrientation = "orientation";
        /**
        * Use Printer Defaults
        */
        public static final String AttrUsePrinterDefaults = "usePrinterDefaults";
        /**
        * Black And White
        */
        public static final String AttrBlackAndWhite = "blackAndWhite";
        /**
        * Draft
        */
        public static final String AttrDraft = "draft";
        /**
        * Print Cell Comments
        */
        public static final String AttrCellComments = "cellComments";
        /**
        * Use First Page Number
        */
        public static final String AttrUseFirstPageNumber = "useFirstPageNumber";
        /**
        * Print Error Handling
        */
        public static final String AttrErrors = "errors";
        /**
        * Horizontal DPI
        */
        public static final String AttrHorizontalDpi = "horizontalDpi";
        /**
        * Vertical DPI
        */
        public static final String AttrVerticalDpi = "verticalDpi";
        /**
        * Number Of Copies
        */
        public static final String AttrCopies = "copies";
        /**
        * Different Odd Even Header Footer
        */
        public static final String AttrDifferentOddEven = "differentOddEven";
        /**
        * Different First Page
        */
        public static final String AttrDifferentFirst = "differentFirst";
        /// <summary>
        /// Scale Header & Footer With Document
        /// </summary>
        public static final String AttrScaleWithDoc = "scaleWithDoc";
        /**
        * Align Margins
        */
        public static final String AttrAlignWithMargins = "alignWithMargins";
        /**
        * Current Scenario
        */
        public static final String AttrCurrent = "current";
        /**
        * Last Shown Scenario
        */
        public static final String AttrShow = "show";
        /**
        * Password
        */
        public static final String AttrPassword = "password";
        /**
        * Objects Locked
        */
        public static final String AttrObjects = "objects";
        /**
        * Format Cells Locked
        */
        public static final String AttrFormatCells = "formatCells";
        /**
        * Format Columns Locked
        */
        public static final String AttrFormatColumns = "formatColumns";
        /**
        * Format Rows Locked
        */
        public static final String AttrFormatRows = "formatRows";
        /**
        * Insert Columns Locked
        */
        public static final String AttrInsertColumns = "insertColumns";
        /**
        * Insert Rows Locked
        */
        public static final String AttrInsertRows = "insertRows";
        /**
        * Insert Hyperlinks Locked
        */
        public static final String AttrInsertHyperlinks = "insertHyperlinks";
        /**
        * Delete Columns Locked
        */
        public static final String AttrDeleteColumns = "deleteColumns";
        /**
        * Delete Rows Locked
        */
        public static final String AttrDeleteRows = "deleteRows";
        /**
        * Select Locked Cells Locked
        */
        public static final String AttrSelectLockedCells = "selectLockedCells";
        /**
        * Sort Locked
        */
        public static final String AttrSort = "sort";
        /**
        * Pivot Tables Locked
        */
        public static final String AttrPivotTables = "pivotTables";
        /**
        * Select Unlocked Cells Locked
        */
        public static final String AttrSelectUnlockedCells = "selectUnlockedCells";
        /**
        * Security Descriptor
        */
        public static final String AttrSecurityDescriptor = "securityDescriptor";
        /**
        * Scenario Locked
        */
        public static final String AttrLocked = "locked";
        /**
        * User Name
        */
        public static final String AttrUser = "user";
        /**
        * Scenario Comment
        */
        public static final String AttrComment = "comment";
        /**
        * Undone
        */
        public static final String AttrUndone = "undone";
        /**
        * Number Format Id
        */
        public static final String AttrNumFmtId = "numFmtId";
        /**
        * Zoom To Fit
        */
        public static final String AttrZoomToFit = "zoomToFit";
        /**
        * Contents
        */
        public static final String AttrContent = "content";
        /**
        * OLE ProgId
        */
        public static final String AttrProgId = "progId";
        /**
        * Data or View Aspect
        */
        public static final String AttrDvAspect = "dvAspect";
        /**
        * OLE Update
        */
        public static final String AttrOleUpdate = "oleUpdate";
        /**
        * Auto Load
        */
        public static final String AttrAutoLoad = "autoLoad";
        /**
        * Shape Id
        */
        public static final String AttrShapeId = "shapeId";
        /**
        * Destination Bookmark
        */
        public static final String AttrDivId = "divId";
        /**
        * Web Source Type
        */
        public static final String AttrSourceType = "sourceType";
        /**
        * Source Id
        */
        public static final String AttrSourceRef = "sourceRef";
        /**
        * Source Object Name
        */
        public static final String AttrSourceObject = "sourceObject";
        /**
        * Destination File Name
        */
        public static final String AttrDestinationFile = "destinationFile";
        /**
        * Title
        */
        public static final String AttrTitle = "title";
        /**
        * Automatically Publish
        */
        public static final String AttrAutoRepublish = "autoRepublish";
        /**
        * Evaluation Error
        */
        public static final String AttrEvalError = "evalError";
        /**
        * Two Digit Text Year
        */
        public static final String AttrTwoDigitTextYear = "twoDigitTextYear";
        /**
        * Number Stored As Text
        */
        public static final String AttrNumberStoredAsText = "numberStoredAsText";
        /**
        * Formula Range
        */
        public static final String AttrFormulaRange = "formulaRange";
        /**
        * Unlocked Formula
        */
        public static final String AttrUnlockedFormula = "unlockedFormula";
        /**
        * Empty Cell Reference
        */
        public static final String AttrEmptyCellReference = "emptyCellReference";
        /**
        * List Data Validation
        */
        public static final String AttrListDataValidation = "listDataValidation";
        /**
        * Calculated Column
        */
        public static final String AttrCalculatedColumn = "calculatedColumn";
    }

    public static class SheetMetadata   
    {
        /**
        * Metadata
        */
        public static final String ElMetadata = "metadata";
        /**
        * Metadata Types Collection
        */
        public static final String ElMetadataTypes = "metadataTypes";
        /**
        * Metadata String Store
        */
        public static final String ElMetadataStrings = "metadataStrings";
        /**
        * MDX Metadata Information
        */
        public static final String ElMdxMetadata = "mdxMetadata";
        /**
        * Future Metadata
        */
        public static final String ElFutureMetadata = "futureMetadata";
        /**
        * Cell Metadata
        */
        public static final String ElCellMetadata = "cellMetadata";
        /**
        * Value Metadata
        */
        public static final String ElValueMetadata = "valueMetadata";
        /**
        * Future Feature Storage Area
        */
        public static final String ElExtLst = "extLst";
        /**
        * Metadata Type Information
        */
        public static final String ElMetadataType = "metadataType";
        /**
        * Metadata Block
        */
        public static final String ElBk = "bk";
        /**
        * Metadata Record
        */
        public static final String ElRc = "rc";
        /**
        * MDX Metadata Record
        */
        public static final String ElMdx = "mdx";
        /**
        * Tuple MDX Metadata
        */
        public static final String ElT = "t";
        /**
        * Set MDX Metadata
        */
        public static final String ElMs = "ms";
        /**
        * Member Property MDX Metadata
        */
        public static final String ElP = "p";
        /**
        * KPI MDX Metadata
        */
        public static final String ElK = "k";
        /**
        * Member Unique Name Index
        */
        public static final String ElN = "n";
        /**
        * MDX Metadata String
        */
        public static final String ElS = "s";
        /**
        * Metadata Type Count
        */
        public static final String AttrCount = "count";
        /**
        * Metadata Type Name
        */
        public static final String AttrName = "name";
        /**
        * Minimum Supported Version
        */
        public static final String AttrMinSupportedVersion = "minSupportedVersion";
        /**
        * Metadata Ghost Row
        */
        public static final String AttrGhostRow = "ghostRow";
        /**
        * Metadata Ghost Column
        */
        public static final String AttrGhostCol = "ghostCol";
        /**
        * Metadata Edit
        */
        public static final String AttrEdit = "edit";
        /**
        * Metadata Cell Value Delete
        */
        public static final String AttrDelete = "delete";
        /**
        * Metadata Copy
        */
        public static final String AttrCopy = "copy";
        /**
        * Metadata Paste All
        */
        public static final String AttrPasteAll = "pasteAll";
        /**
        * Metadata Paste Formulas
        */
        public static final String AttrPasteFormulas = "pasteFormulas";
        /**
        * Metadata Paste Special Values
        */
        public static final String AttrPasteValues = "pasteValues";
        /**
        * Metadata Paste Formats
        */
        public static final String AttrPasteFormats = "pasteFormats";
        /**
        * Metadata Paste Comments
        */
        public static final String AttrPasteComments = "pasteComments";
        /**
        * Metadata Paste Data Validation
        */
        public static final String AttrPasteDataValidation = "pasteDataValidation";
        /**
        * Metadata Paste Borders
        */
        public static final String AttrPasteBorders = "pasteBorders";
        /**
        * Metadata Paste Column Widths
        */
        public static final String AttrPasteColWidths = "pasteColWidths";
        /**
        * Metadata Paste Number Formats
        */
        public static final String AttrPasteNumberFormats = "pasteNumberFormats";
        /**
        * Metadata Merge
        */
        public static final String AttrMerge = "merge";
        /**
        * Meatadata Split First
        */
        public static final String AttrSplitFirst = "splitFirst";
        /**
        * Metadata Split All
        */
        public static final String AttrSplitAll = "splitAll";
        /**
        * Metadata Insert Delete
        */
        public static final String AttrRowColShift = "rowColShift";
        /**
        * Metadata Clear All
        */
        public static final String AttrClearAll = "clearAll";
        /**
        * Metadata Clear Formats
        */
        public static final String AttrClearFormats = "clearFormats";
        /**
        * Metadata Clear Contents
        */
        public static final String AttrClearContents = "clearContents";
        /**
        * Metadata Clear Comments
        */
        public static final String AttrClearComments = "clearComments";
        /**
        * Metadata Formula Assignment
        */
        public static final String AttrAssign = "assign";
        /**
        * Metadata Coercion
        */
        public static final String AttrCoerce = "coerce";
        /**
        * Adjust Metadata
        */
        public static final String AttrAdjust = "adjust";
        /**
        * Cell Metadata
        */
        public static final String AttrCellMeta = "cellMeta";
        /**
        * Metadata Record Value Index
        */
        public static final String AttrV = "v";
        /**
        * Cube Function Tag
        */
        public static final String AttrF = "f";
        /**
        * Member Index Count
        */
        public static final String AttrC = "c";
        /**
        * Server Formatting Culture Currency
        */
        public static final String AttrCt = "ct";
        /**
        * Server Formatting String Index
        */
        public static final String AttrSi = "si";
        /**
        * Server Formatting Built-In Number Format Index
        */
        public static final String AttrFi = "fi";
        /**
        * Server Formatting Background Color
        */
        public static final String AttrBc = "bc";
        /**
        * Server Formatting Foreground Color
        */
        public static final String AttrFc = "fc";
        /**
        * Server Formatting Italic Font
        */
        public static final String AttrI = "i";
        /**
        * Server Formatting Underline Font
        */
        public static final String AttrU = "u";
        /**
        * Server Formatting Strikethrough Font
        */
        public static final String AttrSt = "st";
        /**
        * Server Formatting Bold Font
        */
        public static final String AttrB = "b";
        /**
        * Set Definition Index
        */
        public static final String AttrNs = "ns";
        /**
        * Set Sort Order
        */
        public static final String AttrO = "o";
        /**
        * Property Name Index
        */
        public static final String AttrNp = "np";
        /**
        * Index Value
        */
        public static final String AttrX = "x";
    }

    public static class SingleCellTable   
    {
        /**
        * Single Cells
        */
        public static final String ElSingleXmlCells = "singleXmlCells";
        /**
        * Table Properties
        */
        public static final String ElSingleXmlCell = "singleXmlCell";
        /**
        * Cell Properties
        */
        public static final String ElXmlCellPr = "xmlCellPr";
        /**
        * Future Feature Data Storage Area
        */
        public static final String ElExtLst = "extLst";
        /**
        * Column XML Properties
        */
        public static final String ElXmlPr = "xmlPr";
        /**
        * Table Id
        */
        public static final String AttrId = "id";
        /**
        * Reference
        */
        public static final String AttrR = "r";
        /**
        * Connection ID
        */
        public static final String AttrConnectionId = "connectionId";
        /**
        * Unique Table Name
        */
        public static final String AttrUniqueName = "uniqueName";
        /**
        * XML Map Id
        */
        public static final String AttrMapId = "mapId";
        /**
        * XPath
        */
        public static final String AttrXpath = "xpath";
        /**
        * XML Data Type
        */
        public static final String AttrXmlDataType = "xmlDataType";
    }

    public static class Styles   
    {
        /**
        * Style Sheet
        */
        public static final String ElStyleSheet = "styleSheet";
        /**
        * Number Formats
        */
        public static final String ElNumFmts = "numFmts";
        /**
        * Fonts
        */
        public static final String ElFonts = "fonts";
        /**
        * Fills
        */
        public static final String ElFills = "fills";
        /**
        * Borders
        */
        public static final String ElBorders = "borders";
        /**
        * Formatting Records
        */
        public static final String ElCellStyleXfs = "cellStyleXfs";
        /**
        * Cell Formats
        */
        public static final String ElCellXfs = "cellXfs";
        /**
        * Cell Styles
        */
        public static final String ElCellStyles = "cellStyles";
        /**
        * Formats
        */
        public static final String ElDxfs = "dxfs";
        /**
        * Table Styles
        */
        public static final String ElTableStyles = "tableStyles";
        /**
        * Colors
        */
        public static final String ElColors = "colors";
        /**
        * Future Feature Data Storage Area
        */
        public static final String ElExtLst = "extLst";
        /**
        * Border
        */
        public static final String ElBorder = "border";
        /**
        * Left Border
        */
        public static final String ElLeft = "left";
        /**
        * Right Border
        */
        public static final String ElRight = "right";
        /**
        * Top Border
        */
        public static final String ElTop = "top";
        /**
        * Bottom Border
        */
        public static final String ElBottom = "bottom";
        /**
        * Diagonal
        */
        public static final String ElDiagonal = "diagonal";
        /**
        * Vertical Inner Border
        */
        public static final String ElVertical = "vertical";
        /**
        * Horizontal Inner Borders
        */
        public static final String ElHorizontal = "horizontal";
        /**
        * Color
        */
        public static final String ElColor = "color";
        /**
        * Font
        */
        public static final String ElFont = "font";
        /**
        * Fill
        */
        public static final String ElFill = "fill";
        /**
        * Pattern
        */
        public static final String ElPatternFill = "patternFill";
        /**
        * Gradient
        */
        public static final String ElGradientFill = "gradientFill";
        /**
        * Foreground Color
        */
        public static final String ElFgColor = "fgColor";
        /**
        * Background Color
        */
        public static final String ElBgColor = "bgColor";
        /**
        * Gradient Stop
        */
        public static final String ElStop = "stop";
        /**
        * Number Formats
        */
        public static final String ElNumFmt = "numFmt";
        /**
        * Formatting Elements
        */
        public static final String ElXf = "xf";
        /**
        * Alignment
        */
        public static final String ElAlignment = "alignment";
        /**
        * Protection
        */
        public static final String ElProtection = "protection";
        /**
        * Cell Style
        */
        public static final String ElCellStyle = "cellStyle";
        /**
        * Formatting
        */
        public static final String ElDxf = "dxf";
        /**
        * Color Indexes
        */
        public static final String ElIndexedColors = "indexedColors";
        /**
        * MRU Colors
        */
        public static final String ElMruColors = "mruColors";
        /**
        * RGB Color
        */
        public static final String ElRgbColor = "rgbColor";
        /**
        * Table Style
        */
        public static final String ElTableStyle = "tableStyle";
        /**
        * Table Style
        */
        public static final String ElTableStyleElement = "tableStyleElement";
        /**
        * Font Name
        */
        public static final String ElName = "name";
        /**
        * Character Set
        */
        public static final String ElCharset = "charset";
        /**
        * Font Family
        */
        public static final String ElFamily = "family";
        /**
        * Bold
        */
        public static final String ElB = "b";
        /**
        * Italic
        */
        public static final String ElI = "i";
        /**
        * Strike Through
        */
        public static final String ElStrike = "strike";
        /**
        * Outline
        */
        public static final String ElOutline = "outline";
        /**
        * Shadow
        */
        public static final String ElShadow = "shadow";
        /**
        * Condense
        */
        public static final String ElCondense = "condense";
        /**
        * Extend
        */
        public static final String ElExtend = "extend";
        /**
        * Font Size
        */
        public static final String ElSz = "sz";
        /**
        * Underline
        */
        public static final String ElU = "u";
        /**
        * Text Vertical Alignment
        */
        public static final String ElVertAlign = "vertAlign";
        /**
        * Scheme
        */
        public static final String ElScheme = "scheme";
        /**
        * Text Rotation
        */
        public static final String AttrTextRotation = "textRotation";
        /**
        * Wrap Text
        */
        public static final String AttrWrapText = "wrapText";
        /**
        * Indent
        */
        public static final String AttrIndent = "indent";
        /**
        * Relative Indent
        */
        public static final String AttrRelativeIndent = "relativeIndent";
        /**
        * Justify Last Line
        */
        public static final String AttrJustifyLastLine = "justifyLastLine";
        /**
        * Shrink To Fit
        */
        public static final String AttrShrinkToFit = "shrinkToFit";
        /**
        * Reading Order
        */
        public static final String AttrReadingOrder = "readingOrder";
        /**
        * Border Count
        */
        public static final String AttrCount = "count";
        /**
        * Diagonal Up
        */
        public static final String AttrDiagonalUp = "diagonalUp";
        /**
        * Diagonal Down
        */
        public static final String AttrDiagonalDown = "diagonalDown";
        /**
        * Line Style
        */
        public static final String AttrStyle = "style";
        /**
        * Cell Locked
        */
        public static final String AttrLocked = "locked";
        /**
        * Hidden Cell
        */
        public static final String AttrHidden = "hidden";
        /**
        * Pattern Type
        */
        public static final String AttrPatternType = "patternType";
        /**
        * Automatic
        */
        public static final String AttrAuto = "auto";
        /**
        * Index
        */
        public static final String AttrIndexed = "indexed";
        /**
        * Alpha Red Green Blue Color Value
        */
        public static final String AttrRgb = "rgb";
        /**
        * Theme Color
        */
        public static final String AttrTheme = "theme";
        /**
        * Tint
        */
        public static final String AttrTint = "tint";
        /**
        * Gradient Fill Type
        */
        public static final String AttrType = "type";
        /**
        * Linear Gradient Degree
        */
        public static final String AttrDegree = "degree";
        /**
        * Gradient Stop Position
        */
        public static final String AttrPosition = "position";
        /**
        * Number Format Id
        */
        public static final String AttrNumFmtId = "numFmtId";
        /**
        * Number Format Code
        */
        public static final String AttrFormatCode = "formatCode";
        /**
        * Font Id
        */
        public static final String AttrFontId = "fontId";
        /**
        * Fill Id
        */
        public static final String AttrFillId = "fillId";
        /**
        * Border Id
        */
        public static final String AttrBorderId = "borderId";
        /**
        * Format Id
        */
        public static final String AttrXfId = "xfId";
        /**
        * Quote Prefix
        */
        public static final String AttrQuotePrefix = "quotePrefix";
        /**
        * Pivot Button
        */
        public static final String AttrPivotButton = "pivotButton";
        /**
        * Apply Number Format
        */
        public static final String AttrApplyNumberFormat = "applyNumberFormat";
        /**
        * Apply Font
        */
        public static final String AttrApplyFont = "applyFont";
        /**
        * Apply Fill
        */
        public static final String AttrApplyFill = "applyFill";
        /**
        * Apply Border
        */
        public static final String AttrApplyBorder = "applyBorder";
        /**
        * Apply Alignment
        */
        public static final String AttrApplyAlignment = "applyAlignment";
        /**
        * Apply Protection
        */
        public static final String AttrApplyProtection = "applyProtection";
        /**
        * Built-In Style Id
        */
        public static final String AttrBuiltinId = "builtinId";
        /**
        * Outline Style
        */
        public static final String AttrILevel = "iLevel";
        /**
        * Custom Built In
        */
        public static final String AttrCustomBuiltin = "customBuiltin";
        /**
        * Default Table Style
        */
        public static final String AttrDefaultTableStyle = "defaultTableStyle";
        /**
        * Default Pivot Style
        */
        public static final String AttrDefaultPivotStyle = "defaultPivotStyle";
        /**
        * Pivot Style
        */
        public static final String AttrPivot = "pivot";
        /**
        * Table
        */
        public static final String AttrTable = "table";
        /**
        * Band Size
        */
        public static final String AttrSize = "size";
        /**
        * Formatting Id
        */
        public static final String AttrDxfId = "dxfId";
        /**
        * Value
        */
        public static final String AttrVal = "val";
        /**
        * Auto Format Id
        */
        public static final String AttrAutoFormatId = "autoFormatId";
        /**
        * Apply Number Formats
        */
        public static final String AttrApplyNumberFormats = "applyNumberFormats";
        /**
        * Apply Border Formats
        */
        public static final String AttrApplyBorderFormats = "applyBorderFormats";
        /**
        * Apply Font Formats
        */
        public static final String AttrApplyFontFormats = "applyFontFormats";
        /**
        * Apply Pattern Formats
        */
        public static final String AttrApplyPatternFormats = "applyPatternFormats";
        /**
        * Apply Alignment Formats
        */
        public static final String AttrApplyAlignmentFormats = "applyAlignmentFormats";
        /**
        * Apply Width / Height Formats
        */
        public static final String AttrApplyWidthHeightFormats = "applyWidthHeightFormats";
    }

    public static class SupplementaryWorkbooks   
    {
        /**
        * External Reference
        */
        public static final String ElExternalLink = "externalLink";
        /**
        * External Workbook
        */
        public static final String ElExternalBook = "externalBook";
        /**
        * DDE Connection
        */
        public static final String ElDdeLink = "ddeLink";
        /**
        * OLE Link
        */
        public static final String ElOleLink = "oleLink";
        /**
        * 
        */
        public static final String ElExtLst = "extLst";
        /**
        * Supporting Workbook Sheet Names
        */
        public static final String ElSheetNames = "sheetNames";
        /**
        * Named Links
        */
        public static final String ElDefinedNames = "definedNames";
        /**
        * Cached Worksheet Data
        */
        public static final String ElSheetDataSet = "sheetDataSet";
        /**
        * Sheet Name
        */
        public static final String ElSheetName = "sheetName";
        /**
        * Defined Name
        */
        public static final String ElDefinedName = "definedName";
        /**
        * External Sheet Data Set
        */
        public static final String ElSheetData = "sheetData";
        /**
        * Row
        */
        public static final String ElRow = "row";
        /**
        * External Cell Data
        */
        public static final String ElCell = "cell";
        /**
        * Value
        */
        public static final String ElV = "v";
        /**
        * DDE Items Collection
        */
        public static final String ElDdeItems = "ddeItems";
        /**
        * DDE Item definition
        */
        public static final String ElDdeItem = "ddeItem";
        /**
        * DDE Name Values
        */
        public static final String ElValues = "values";
        /**
        * Value
        */
        public static final String ElValue = "value";
        /**
        * DDE Link Value
        */
        public static final String ElVal = "val";
        /**
        * OLE Link Items
        */
        public static final String ElOleItems = "oleItems";
        /**
        * OLE Link Item
        */
        public static final String ElOleItem = "oleItem";
        /**
        * Defined Name
        */
        public static final String AttrName = "name";
        /**
        * Refers To
        */
        public static final String AttrRefersTo = "refersTo";
        /**
        * Sheet Id
        */
        public static final String AttrSheetId = "sheetId";
        /**
        * Last Refresh Resulted in Error
        */
        public static final String AttrRefreshError = "refreshError";
        /**
        * Row
        */
        public static final String AttrR = "r";
        /**
        * Type
        */
        public static final String AttrT = "t";
        /**
        * Value Metadata
        */
        public static final String AttrVm = "vm";
        /**
        * Service name
        */
        public static final String AttrDdeService = "ddeService";
        /**
        * Topic for DDE server
        */
        public static final String AttrDdeTopic = "ddeTopic";
        /**
        * OLE
        */
        public static final String AttrOle = "ole";
        /**
        * Advise
        */
        public static final String AttrAdvise = "advise";
        /**
        * Data is an Image
        */
        public static final String AttrPreferPic = "preferPic";
        /**
        * Rows
        */
        public static final String AttrRows = "rows";
        /**
        * Columns
        */
        public static final String AttrCols = "cols";
        /**
        * OLE Link ProgID
        */
        public static final String AttrProgId = "progId";
        /**
        * Icon
        */
        public static final String AttrIcon = "icon";
    }

    public static class Table   
    {
        /**
        * Table
        */
        public static final String ElTable = "table";
        /**
        * Table AutoFilter
        */
        public static final String ElAutoFilter = "autoFilter";
        /**
        * Sort State
        */
        public static final String ElSortState = "sortState";
        /**
        * Table Columns
        */
        public static final String ElTableColumns = "tableColumns";
        /**
        * Table Style
        */
        public static final String ElTableStyleInfo = "tableStyleInfo";
        /**
        * Future Feature Data Storage Area
        */
        public static final String ElExtLst = "extLst";
        /**
        * Table Column
        */
        public static final String ElTableColumn = "tableColumn";
        /**
        * Calculated Column Formula
        */
        public static final String ElCalculatedColumnFormula = "calculatedColumnFormula";
        /**
        * Totals Row Formula
        */
        public static final String ElTotalsRowFormula = "totalsRowFormula";
        /**
        * XML Column Properties
        */
        public static final String ElXmlColumnPr = "xmlColumnPr";
        /**
        * Table Id
        */
        public static final String AttrId = "id";
        /**
        * Name
        */
        public static final String AttrName = "name";
        /**
        * Table Name
        */
        public static final String AttrDisplayName = "displayName";
        /**
        * Table Comment
        */
        public static final String AttrComment = "comment";
        /**
        * Reference
        */
        public static final String AttrRef = "ref";
        /**
        * Table Type
        */
        public static final String AttrTableType = "tableType";
        /**
        * Header Row Count
        */
        public static final String AttrHeaderRowCount = "headerRowCount";
        /**
        * Insert Row Showing
        */
        public static final String AttrInsertRow = "insertRow";
        /**
        * Insert Row Shift
        */
        public static final String AttrInsertRowShift = "insertRowShift";
        /**
        * Totals Row Count
        */
        public static final String AttrTotalsRowCount = "totalsRowCount";
        /**
        * Totals Row Shown
        */
        public static final String AttrTotalsRowShown = "totalsRowShown";
        /**
        * Published
        */
        public static final String AttrPublished = "published";
        /**
        * Header Row Format Id
        */
        public static final String AttrHeaderRowDxfId = "headerRowDxfId";
        /**
        * Data Area Format Id
        */
        public static final String AttrDataDxfId = "dataDxfId";
        /**
        * Totals Row Format Id
        */
        public static final String AttrTotalsRowDxfId = "totalsRowDxfId";
        /**
        * Header Row Border Format Id
        */
        public static final String AttrHeaderRowBorderDxfId = "headerRowBorderDxfId";
        /**
        * Table Border Format Id
        */
        public static final String AttrTableBorderDxfId = "tableBorderDxfId";
        /**
        * Totals Row Border Format Id
        */
        public static final String AttrTotalsRowBorderDxfId = "totalsRowBorderDxfId";
        /**
        * Header Row Style
        */
        public static final String AttrHeaderRowCellStyle = "headerRowCellStyle";
        /**
        * Data Style Name
        */
        public static final String AttrDataCellStyle = "dataCellStyle";
        /**
        * Totals Row Style
        */
        public static final String AttrTotalsRowCellStyle = "totalsRowCellStyle";
        /**
        * Connection ID
        */
        public static final String AttrConnectionId = "connectionId";
        /**
        * Show First Column
        */
        public static final String AttrShowFirstColumn = "showFirstColumn";
        /**
        * Show Last Column
        */
        public static final String AttrShowLastColumn = "showLastColumn";
        /**
        * Show Row Stripes
        */
        public static final String AttrShowRowStripes = "showRowStripes";
        /**
        * Show Column Stripes
        */
        public static final String AttrShowColumnStripes = "showColumnStripes";
        /**
        * Column Count
        */
        public static final String AttrCount = "count";
        /**
        * Unique Name
        */
        public static final String AttrUniqueName = "uniqueName";
        /**
        * Totals Row Function
        */
        public static final String AttrTotalsRowFunction = "totalsRowFunction";
        /**
        * Totals Row Label
        */
        public static final String AttrTotalsRowLabel = "totalsRowLabel";
        /**
        * Query Table Field Id
        */
        public static final String AttrQueryTableFieldId = "queryTableFieldId";
        /**
        * Array
        */
        public static final String AttrArray = "array";
        /**
        * XML Map Id
        */
        public static final String AttrMapId = "mapId";
        /**
        * XPath
        */
        public static final String AttrXpath = "xpath";
        /**
        * Denormalized
        */
        public static final String AttrDenormalized = "denormalized";
        /**
        * XML Data Type
        */
        public static final String AttrXmlDataType = "xmlDataType";
    }

    public static class VolatileDependencies   
    {
        /**
        * Volatile Dependency Types
        */
        public static final String ElVolTypes = "volTypes";
        /**
        * Volatile Dependency Type
        */
        public static final String ElVolType = "volType";
        /**
        * 
        */
        public static final String ElExtLst = "extLst";
        /**
        * Main
        */
        public static final String ElMain = "main";
        /**
        * Topic
        */
        public static final String ElTp = "tp";
        /**
        * Topic Value
        */
        public static final String ElV = "v";
        /**
        * Strings in Subtopic
        */
        public static final String ElStp = "stp";
        /**
        * References
        */
        public static final String ElTr = "tr";
        /**
        * Type
        */
        public static final String AttrType = "type";
        /**
        * First String
        */
        public static final String AttrFirst = "first";
        /**
        * Type
        */
        public static final String AttrT = "t";
        /**
        * Reference
        */
        public static final String AttrR = "r";
        /**
        * Sheet Id
        */
        public static final String AttrS = "s";
    }

    public static class Workbook   
    {
        /**
        * Workbook
        */
        public static final String ElWorkbook = "workbook";
        /**
        * File Version
        */
        public static final String ElFileVersion = "fileVersion";
        /**
        * File Sharing
        */
        public static final String ElFileSharing = "fileSharing";
        /**
        * Workbook Properties
        */
        public static final String ElWorkbookPr = "workbookPr";
        /**
        * Workbook Protection
        */
        public static final String ElWorkbookProtection = "workbookProtection";
        /**
        * Workbook Views
        */
        public static final String ElBookViews = "bookViews";
        /**
        * Sheets
        */
        public static final String ElSheets = "sheets";
        /**
        * Function Groups
        */
        public static final String ElFunctionGroups = "functionGroups";
        /**
        * External References
        */
        public static final String ElExternalReferences = "externalReferences";
        /**
        * Defined Names
        */
        public static final String ElDefinedNames = "definedNames";
        /**
        * Calculation Properties
        */
        public static final String ElCalcPr = "calcPr";
        /**
        * OLE Size
        */
        public static final String ElOleSize = "oleSize";
        /**
        * Custom Workbook Views
        */
        public static final String ElCustomWorkbookViews = "customWorkbookViews";
        /**
        * PivotCaches
        */
        public static final String ElPivotCaches = "pivotCaches";
        /**
        * Smart Tag Properties
        */
        public static final String ElSmartTagPr = "smartTagPr";
        /**
        * Smart Tag Types
        */
        public static final String ElSmartTagTypes = "smartTagTypes";
        /**
        * Web Publishing Properties
        */
        public static final String ElWebPublishing = "webPublishing";
        /**
        * File Recovery Properties
        */
        public static final String ElFileRecoveryPr = "fileRecoveryPr";
        /**
        * Web Publish Objects
        */
        public static final String ElWebPublishObjects = "webPublishObjects";
        /**
        * Future Feature Data Storage Area
        */
        public static final String ElExtLst = "extLst";
        /**
        * Workbook View
        */
        public static final String ElWorkbookView = "workbookView";
        /**
        * Custom Workbook View
        */
        public static final String ElCustomWorkbookView = "customWorkbookView";
        /**
        * Sheet Information
        */
        public static final String ElSheet = "sheet";
        /**
        * Smart Tag Type
        */
        public static final String ElSmartTagType = "smartTagType";
        /**
        * Defined Name
        */
        public static final String ElDefinedName = "definedName";
        /**
        * External Reference
        */
        public static final String ElExternalReference = "externalReference";
        /**
        * PivotCache
        */
        public static final String ElPivotCache = "pivotCache";
        /**
        * Function Group
        */
        public static final String ElFunctionGroup = "functionGroup";
        /**
        * Web Publishing Object
        */
        public static final String ElWebPublishObject = "webPublishObject";
        /**
        * Application Name
        */
        public static final String AttrAppName = "appName";
        /**
        * Last Edited Version
        */
        public static final String AttrLastEdited = "lastEdited";
        /**
        * Lowest Edited Version
        */
        public static final String AttrLowestEdited = "lowestEdited";
        /**
        * Build Version
        */
        public static final String AttrRupBuild = "rupBuild";
        /**
        * Code Name
        */
        public static final String AttrCodeName = "codeName";
        /**
        * Visibility
        */
        public static final String AttrVisibility = "visibility";
        /**
        * Minimized
        */
        public static final String AttrMinimized = "minimized";
        /**
        * Show Horizontal Scroll
        */
        public static final String AttrShowHorizontalScroll = "showHorizontalScroll";
        /**
        * Show Vertical Scroll
        */
        public static final String AttrShowVerticalScroll = "showVerticalScroll";
        /**
        * Show Sheet Tabs
        */
        public static final String AttrShowSheetTabs = "showSheetTabs";
        /**
        * Upper Left Corner (X Coordinate)
        */
        public static final String AttrXWindow = "xWindow";
        /**
        * Upper Left Corner (Y Coordinate)
        */
        public static final String AttrYWindow = "yWindow";
        /**
        * Window Width
        */
        public static final String AttrWindowWidth = "windowWidth";
        /**
        * Window Height
        */
        public static final String AttrWindowHeight = "windowHeight";
        /**
        * Sheet Tab Ratio
        */
        public static final String AttrTabRatio = "tabRatio";
        /**
        * First Sheet
        */
        public static final String AttrFirstSheet = "firstSheet";
        /**
        * Active Sheet Index
        */
        public static final String AttrActiveTab = "activeTab";
        /**
        * AutoFilter Date Grouping
        */
        public static final String AttrAutoFilterDateGrouping = "autoFilterDateGrouping";
        /**
        * Custom View Name
        */
        public static final String AttrName = "name";
        /**
        * Custom View GUID
        */
        public static final String AttrGuid = "guid";
        /**
        * Auto Update
        */
        public static final String AttrAutoUpdate = "autoUpdate";
        /**
        * Merge Interval
        */
        public static final String AttrMergeInterval = "mergeInterval";
        /**
        * Changes Saved Win
        */
        public static final String AttrChangesSavedWin = "changesSavedWin";
        /**
        * Only Synch
        */
        public static final String AttrOnlySync = "onlySync";
        /**
        * Personal View
        */
        public static final String AttrPersonalView = "personalView";
        /**
        * Include Print Settings
        */
        public static final String AttrIncludePrintSettings = "includePrintSettings";
        /// <summary>
        /// Include Hidden Rows & Columns
        /// </summary>
        public static final String AttrIncludeHiddenRowCol = "includeHiddenRowCol";
        /**
        * Maximized
        */
        public static final String AttrMaximized = "maximized";
        /**
        * Active Sheet in Book View
        */
        public static final String AttrActiveSheetId = "activeSheetId";
        /**
        * Show Formula Bar
        */
        public static final String AttrShowFormulaBar = "showFormulaBar";
        /**
        * Show Status Bar
        */
        public static final String AttrShowStatusbar = "showStatusbar";
        /**
        * Show Comments
        */
        public static final String AttrShowComments = "showComments";
        /**
        * Show Objects
        */
        public static final String AttrShowObjects = "showObjects";
        /**
        * Sheet Tab Id
        */
        public static final String AttrSheetId = "sheetId";
        /**
        * Visible State
        */
        public static final String AttrState = "state";
        /**
        * Date 1904
        */
        public static final String AttrDate1904 = "date1904";
        /**
        * Show Border Unselected Table
        */
        public static final String AttrShowBorderUnselectedTables = "showBorderUnselectedTables";
        /**
        * Filter Privacy
        */
        public static final String AttrFilterPrivacy = "filterPrivacy";
        /**
        * Prompted Solutions
        */
        public static final String AttrPromptedSolutions = "promptedSolutions";
        /**
        * Show Ink Annotations
        */
        public static final String AttrShowInkAnnotation = "showInkAnnotation";
        /**
        * Create Backup File
        */
        public static final String AttrBackupFile = "backupFile";
        /**
        * Save External Link Values
        */
        public static final String AttrSaveExternalLinkValues = "saveExternalLinkValues";
        /**
        * Update Links Behavior
        */
        public static final String AttrUpdateLinks = "updateLinks";
        /**
        * Hide Pivot Field List
        */
        public static final String AttrHidePivotFieldList = "hidePivotFieldList";
        /**
        * Show Pivot Chart Filter
        */
        public static final String AttrShowPivotChartFilter = "showPivotChartFilter";
        /**
        * Allow Refresh Query
        */
        public static final String AttrAllowRefreshQuery = "allowRefreshQuery";
        /**
        * Publish Items
        */
        public static final String AttrPublishItems = "publishItems";
        /**
        * Check Compatibility On Save
        */
        public static final String AttrCheckCompatibility = "checkCompatibility";
        /**
        * Auto Compress Pictures
        */
        public static final String AttrAutoCompressPictures = "autoCompressPictures";
        /**
        * Refresh all Connections on Open
        */
        public static final String AttrRefreshAllConnections = "refreshAllConnections";
        /**
        * Default Theme Version
        */
        public static final String AttrDefaultThemeVersion = "defaultThemeVersion";
        /**
        * Embed SmartTags
        */
        public static final String AttrEmbed = "embed";
        /**
        * Show Smart Tags
        */
        public static final String AttrShow = "show";
        /**
        * SmartTag Namespace URI
        */
        public static final String AttrNamespaceUri = "namespaceUri";
        /**
        * Smart Tag URL
        */
        public static final String AttrUrl = "url";
        /**
        * Auto Recover
        */
        public static final String AttrAutoRecover = "autoRecover";
        /**
        * Crash Save
        */
        public static final String AttrCrashSave = "crashSave";
        /**
        * Data Extract Load
        */
        public static final String AttrDataExtractLoad = "dataExtractLoad";
        /**
        * Repair Load
        */
        public static final String AttrRepairLoad = "repairLoad";
        /**
        * Calculation Id
        */
        public static final String AttrCalcId = "calcId";
        /**
        * Calculation Mode
        */
        public static final String AttrCalcMode = "calcMode";
        /**
        * Full Calculation On Load
        */
        public static final String AttrFullCalcOnLoad = "fullCalcOnLoad";
        /**
        * Reference Mode
        */
        public static final String AttrRefMode = "refMode";
        /**
        * Calculation Iteration
        */
        public static final String AttrIterate = "iterate";
        /**
        * Iteration Count
        */
        public static final String AttrIterateCount = "iterateCount";
        /**
        * Iterative Calculation Delta
        */
        public static final String AttrIterateDelta = "iterateDelta";
        /**
        * Full Precision Calculation
        */
        public static final String AttrFullPrecision = "fullPrecision";
        /**
        * Calc Completed
        */
        public static final String AttrCalcCompleted = "calcCompleted";
        /**
        * Calculate On Save
        */
        public static final String AttrCalcOnSave = "calcOnSave";
        /**
        * Concurrent Calculations
        */
        public static final String AttrConcurrentCalc = "concurrentCalc";
        /**
        * Concurrent Thread Manual Count
        */
        public static final String AttrConcurrentManualCount = "concurrentManualCount";
        /**
        * Force Full Calculation
        */
        public static final String AttrForceFullCalc = "forceFullCalc";
        /**
        * Comment
        */
        public static final String AttrComment = "comment";
        /**
        * Custom Menu Text
        */
        public static final String AttrCustomMenu = "customMenu";
        /**
        * Description
        */
        public static final String AttrDescription = "description";
        /**
        * Help
        */
        public static final String AttrHelp = "help";
        /**
        * Status Bar
        */
        public static final String AttrStatusBar = "statusBar";
        /**
        * Local Name Sheet Id
        */
        public static final String AttrLocalSheetId = "localSheetId";
        /**
        * Hidden Name
        */
        public static final String AttrHidden = "hidden";
        /**
        * Function
        */
        public static final String AttrFunction = "function";
        /**
        * Procedure
        */
        public static final String AttrVbProcedure = "vbProcedure";
        /**
        * External Function
        */
        public static final String AttrXlm = "xlm";
        /**
        * Function Group Id
        */
        public static final String AttrFunctionGroupId = "functionGroupId";
        /**
        * Shortcut Key
        */
        public static final String AttrShortcutKey = "shortcutKey";
        /**
        * Publish To Server
        */
        public static final String AttrPublishToServer = "publishToServer";
        /**
        * Workbook Parameter (Server)
        */
        public static final String AttrWorkbookParameter = "workbookParameter";
        /**
        * PivotCache Id
        */
        public static final String AttrCacheId = "cacheId";
        /**
        * Read Only Recommended
        */
        public static final String AttrReadOnlyRecommended = "readOnlyRecommended";
        /**
        * User Name
        */
        public static final String AttrUserName = "userName";
        /**
        * Write Reservation Password
        */
        public static final String AttrReservationPassword = "reservationPassword";
        /**
        * Reference
        */
        public static final String AttrRef = "ref";
        /**
        * Workbook Password
        */
        public static final String AttrWorkbookPassword = "workbookPassword";
        /**
        * Revisions Password
        */
        public static final String AttrRevisionsPassword = "revisionsPassword";
        /**
        * Lock Structure
        */
        public static final String AttrLockStructure = "lockStructure";
        /**
        * Lock Windows
        */
        public static final String AttrLockWindows = "lockWindows";
        /**
        * Lock Revisions
        */
        public static final String AttrLockRevision = "lockRevision";
        /**
        * Use CSS
        */
        public static final String AttrCss = "css";
        /**
        * Thicket
        */
        public static final String AttrThicket = "thicket";
        /**
        * Enable Long File Names
        */
        public static final String AttrLongFileNames = "longFileNames";
        /**
        * VML in Browsers
        */
        public static final String AttrVml = "vml";
        /**
        * Allow PNG
        */
        public static final String AttrAllowPng = "allowPng";
        /**
        * Target Screen Size
        */
        public static final String AttrTargetScreenSize = "targetScreenSize";
        /**
        * DPI
        */
        public static final String AttrDpi = "dpi";
        /**
        * Code Page
        */
        public static final String AttrCodePage = "codePage";
        /**
        * Built-in Function Group Count
        */
        public static final String AttrBuiltInGroupCount = "builtInGroupCount";
        /**
        * Count
        */
        public static final String AttrCount = "count";
        /**
        * Id
        */
        public static final String AttrId = "id";
        /**
        * Div Id
        */
        public static final String AttrDivId = "divId";
        /**
        * Source Object
        */
        public static final String AttrSourceObject = "sourceObject";
        /**
        * Destination File
        */
        public static final String AttrDestinationFile = "destinationFile";
        /**
        * Title
        */
        public static final String AttrTitle = "title";
        /**
        * Auto Republish
        */
        public static final String AttrAutoRepublish = "autoRepublish";
    }

}



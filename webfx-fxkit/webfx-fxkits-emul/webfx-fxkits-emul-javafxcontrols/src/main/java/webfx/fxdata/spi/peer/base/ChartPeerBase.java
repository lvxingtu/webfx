package webfx.fxdata.spi.peer.base;

import webfx.type.PrimType;
import webfx.type.Type;
import webfx.util.Strings;
import webfx.fxdata.chart.Chart;
import webfx.fxdata.displaydata.DisplayColumn;
import webfx.fxdata.displaydata.DisplayResult;

/**
 * @author Bruno Salmon
 */
public abstract class ChartPeerBase
        <C, N extends Chart, NB extends ChartPeerBase<C, N, NB, NM>, NM extends ChartPeerMixin<C, N, NB, NM>>

        extends SelectableDisplayResultControlPeerBase<C, N, NB, NM> {

    private final boolean isPieChart = this instanceof PieChartPeerBase;

    public void updateResult(DisplayResult rs) {
        if (rs == null)
            return;
        int rowCount = rs.getRowCount();
        int columnCount = rs.getColumnCount();
        DisplayColumn[] columns = rs.getColumns();
        boolean rowFormat = "series".equals(columns[0].getRole());
        boolean hasXAxis = !isPieChart;
        if (!rowFormat) { /***** Column format - see {@link Chart} for format description *****/
            if (isPieChart && rowCount > 1) // ignoring extra rows for pie chart
                rowCount = 1;
            int firstSeriesColumnIndex = hasXAxis ? 1 : 0;
            int seriesCount = columnCount - firstSeriesColumnIndex;
            int pointPerSeriesCount = rowCount;
            Type xType = hasXAxis ? columns[0].getType() : null;
            Type yType = columns[firstSeriesColumnIndex].getType();
            mixin.createChartData(xType, yType, pointPerSeriesCount, seriesCount, seriesIndex -> columns[firstSeriesColumnIndex + seriesIndex].getName());
            for (int pointIndex = 0; pointIndex < pointPerSeriesCount; pointIndex++) {
                if (hasXAxis)
                    mixin.setChartDataX(rs.getValue(pointIndex, 0), pointIndex);
                for (int seriesIndex = 0; seriesIndex < seriesCount; seriesIndex++) {
                    Object yValue = rs.getValue(pointIndex, firstSeriesColumnIndex + seriesIndex);
                    mixin.setChartDataY(yValue, pointIndex, seriesIndex);
                }
            }
        } else {          /***** Row format - see {@link Chart} for format description *****/
            if (isPieChart && columnCount > 2) // ignoring extra columns for pie charts
                columnCount = 2;
            int seriesCount = rowCount;
            int pointPerSeriesCount = columnCount - 1;
            Type xType = hasXAxis ? PrimType.fromObject(columns[1].getName()) : null;
            Type yType = columns[pointPerSeriesCount].getType();
            mixin.createChartData(xType, yType, pointPerSeriesCount, seriesCount, seriesIndex -> Strings.toString(rs.getValue(seriesIndex, 0)));
            for (int pointIndex = 0; pointIndex < pointPerSeriesCount; pointIndex++) {
                if (hasXAxis)
                    mixin.setChartDataX(columns[pointIndex + 1].getName(), pointIndex);
                for (int seriesIndex = 0; seriesIndex < seriesCount; seriesIndex++) {
                    Object yValue = rs.getValue(seriesIndex, pointIndex + 1);
                    mixin.setChartDataY(yValue, pointIndex, seriesIndex);
                }
            }
        }
        mixin.applyChartData();
    }

}
package haui.iroha.service;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import haui.iroha.repository.OrderDetailsRepository;
import haui.iroha.repository.OrdersRepository;
import haui.iroha.repository.ProductsRepository;
import haui.iroha.repository.UserRepository;
import haui.iroha.service.dto.OrderDetailsDTO;
import haui.iroha.service.dto.OrdersDTO;
import haui.iroha.service.dto.ProductsDTO;
import haui.iroha.service.mapper.OrderDetailsMapper;
import haui.iroha.service.mapper.OrdersMapper;
import haui.iroha.service.mapper.ProductsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderPDFExporter {

    private final OrderDetailsRepository orderDetailsRepository;

    private final OrderDetailsMapper orderDetailsMapper;

    private final OrdersRepository ordersRepository;

    private final OrdersMapper ordersMapper;

    private final ProductsRepository productsRepository;

    private final ProductsMapper productsMapper;

    public OrderPDFExporter(OrderDetailsRepository orderDetailsRepository, OrderDetailsMapper orderDetailsMapper,OrdersRepository ordersRepository, OrdersMapper ordersMapper, ProductsRepository productsRepository, ProductsMapper productsMapper) {
        this.orderDetailsRepository = orderDetailsRepository;
        this.orderDetailsMapper = orderDetailsMapper;
        this.ordersRepository = ordersRepository;
        this.ordersMapper = ordersMapper;
        this.productsRepository = productsRepository;
        this.productsMapper = productsMapper;
    }

    private void writeTableHeader(PdfPTable table) throws IOException {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        BaseFont bf = BaseFont.createFont("font/Inter-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font font = new Font(bf);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Tên sản phẩm", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Giá", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Số lượng", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Giá tổng", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table, long id) throws IOException {
        Page<OrderDetailsDTO> ds = orderDetailsRepository.findAllByOrderId(id, Pageable.unpaged()).map(orderDetailsMapper::toDto);
        long allprice = 0;
        BaseFont bf = BaseFont.createFont("font/Inter-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

        Font font = new Font(bf);
        for (OrderDetailsDTO item : ds) {
            ProductsDTO sanpham = productsRepository.findById(item.getIdProduct()).map(productsMapper::toDto).get();
            table.addCell(new Phrase(sanpham.getName(),font));
            table.addCell(new Phrase(String.valueOf(item.getPrice()),font));
            table.addCell(new Phrase(String.valueOf(item.getQuantity()),font));
            table.addCell(new Phrase(String.valueOf(item.getPrice()*item.getQuantity()),font));
            allprice+= item.getPrice()*item.getQuantity();
        }
        table.addCell("");
        table.addCell("");
        table.addCell("");
        table.addCell(new Phrase(String.valueOf(allprice),font));
    }

    public ByteArrayInputStream export(long id) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();
        BaseFont bf = BaseFont.createFont("font/Inter-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Font font2 = new Font(bf);
        font2.setSize(14);
        font2.setColor(Color.BLACK);

        OrdersDTO donhang = ordersRepository.findById(id).map(ordersMapper::toDto).get();

        Paragraph p = new Paragraph("Đơn hàng " + donhang.getOrderCode(), font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        Paragraph p1 = new Paragraph("Người mua: " + donhang.getName(), font2);
        p.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(p1);

        Paragraph p2 = new Paragraph("Số điện thoại: " + donhang.getPhone(), font2);
        p.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(p2);

        Paragraph p3 = new Paragraph("Địa chỉ: " + donhang.getAddress(), font2);
        p.setAlignment(Paragraph.ALIGN_LEFT);

        document.add(p3);

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {3f, 1.5f, 1f, 1.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table, id);

        document.add(table);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");

        Paragraph p4 = new Paragraph("" + donhang.getUpdatedAt().format(formatter.withZone(ZoneId.systemDefault())), font2);
        p4.setAlignment(Paragraph.ALIGN_RIGHT);

        document.add(p4);

        document.close();
        return new ByteArrayInputStream(out.toByteArray());

    }
}

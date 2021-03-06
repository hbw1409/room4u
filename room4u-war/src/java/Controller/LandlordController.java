/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import ViewModel.RoomImage;
import com.google.gson.Gson;
import com.room4u.dao.AccommodationFacadeLocal;
import com.room4u.dao.CommentsFacadeLocal;
import com.room4u.dao.CustomerFacadeLocal;
import com.room4u.dao.OrderRoomFacadeLocal;
import com.room4u.dao.OrderDetailFacadeLocal;
import com.room4u.dao.RatingFacadeLocal;

import com.room4u.model.Accommodation;
import com.room4u.model.Comments;
import com.room4u.model.Customer;

import com.room4u.model.OrderDetail;
import com.room4u.model.OrderRoom;
import com.room4u.model.Rating;

import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DecimalDV;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.primefaces.context.RequestContext;

/**
 *
 * @author NickHo
 */
@ManagedBean(name = "landlord")
@SessionScoped
public class LandlordController {

    @EJB
    private RatingFacadeLocal ratingFacade1;

    @EJB
    private RatingFacadeLocal ratingFacade;

    @EJB
    private OrderDetailFacadeLocal orderDetailFacade;
    @EJB
    private OrderRoomFacadeLocal order1Facade;
    @EJB
    private CommentsFacadeLocal commentsFacade;

    //@Inject
    // Logger log;
    @EJB
    private CustomerFacadeLocal customerFacade;

    @EJB
    private AccommodationFacadeLocal accommodationFacade;

    @ManagedProperty(value = "#{customerBean}")
    private CustomerController customerBean;

    public void setCustomerBean(CustomerController customerBean) {
        this.customerBean = customerBean;
    }

    private Accommodation room = new Accommodation();
    private String houseNumber;
    private String street;
    private String ward;
    private String district;
    private String city;
    private String country;
    private Part thumbnail, file1, file2, file3;
    private List<String> roomImageFileNames;
    private Accommodation curAccom, curAccomUpdate = null;
    private String UpdateRoomResult;

    public String getUpdateRoomResult() {
        return UpdateRoomResult;
    }

    public void setUpdateRoomResult(String UpdateRoomResult) {
        this.UpdateRoomResult = UpdateRoomResult;
    }
    private String deletedAccomId;
    private String deletedRoomResult;

    public String getDeletedRoomResult() {
        return deletedRoomResult;
    }

    public void setDeletedRoomResult(String deletedRoomResult) {
        this.deletedRoomResult = deletedRoomResult;
    }

    public String getDeletedAccomId() {
        return deletedAccomId;
    }

    public void setDeletedAccomId(String deletedAccomId) {
        this.deletedAccomId = deletedAccomId;
    }

    public Accommodation getCurAccomUpdate() {
        return curAccomUpdate;
    }

    public void setCurAccomUpdate(Accommodation curAccomUpdate) {
        this.curAccomUpdate = curAccomUpdate;
    }

    private String slider1, slider2, slider3;
    private int commentsCount;
    private String bookRoomResult;
    private String roomRatingSelected;
    private int displayRate;
    private String commentMessage;

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    public int getDisplayRate() {
        return displayRate;
    }

    public void setDisplayRate(int displayRate) {
        this.displayRate = displayRate;
    }

    public String getRoomRatingSelected() {
        return roomRatingSelected;
    }

    public void setRoomRatingSelected(String roomRatingSelected) {
        this.roomRatingSelected = roomRatingSelected;
    }

    public String getBookRoomResult() {
        return bookRoomResult;
    }

    public void setBookRoomResult(String bookRoomResult) {
        this.bookRoomResult = bookRoomResult;
    }

    public String getSlider1() {
        return slider1;
    }

    public void setSlider1(String slider1) {
        this.slider1 = slider1;
    }

    public String getSlider2() {
        return slider2;
    }

    public void setSlider2(String slider2) {
        this.slider2 = slider2;
    }

    public String getSlider3() {
        return slider3;
    }

    public void setSlider3(String slider3) {
        this.slider3 = slider3;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Accommodation getCurAccom() {
        return curAccom;
    }

    public void setCurAccom(Accommodation curAccom) {
        this.curAccom = curAccom;
    }

    public Part getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Part thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Part getFile2() {
        return file2;
    }

    public void setFile2(Part file2) {
        this.file2 = file2;
    }

    public Part getFile3() {
        return file3;
    }

    public void setFile3(Part file3) {
        this.file3 = file3;
    }

    public Part getFile1() {
        return file1;
    }

    public void setFile1(Part file1) {
        this.file1 = file1;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Accommodation getRoom() {
        return room;
    }

    public void setRoom(Accommodation room) {
        this.room = room;
    }

    public LandlordController() {

    }

    public void createComment() {
        try {
            Comments com = new Comments();
            Date date = new Date();
            com.setComId(1);
            com.setAccomId(curAccom);
            com.setCustId(curAccom.getCustId());
            com.setContent(commentMessage);
            com.setComDate(date);

            commentsFacade.create(com);
        } catch (Exception ex) {
            printStackTrace();
        }
    }

    public List<Accommodation> displayRoom() {

        return accommodationFacade.findAll();
    }

    public void displayAccomUpdate(Accommodation acc) {
        // curAccomUpdate = null;
        curAccomUpdate = new Accommodation();
        curAccomUpdate.setAccomName(acc.getAccomName());
        curAccomUpdate.setDescription(acc.getDescription());
        curAccomUpdate.setAddress(acc.getAddress());
        curAccomUpdate.setNoOfBed(acc.getNoOfBed());
        curAccomUpdate.setNoOfPersons(acc.getNoOfPersons());
        curAccomUpdate.setNoOfToilet(acc.getNoOfToilet());
        curAccomUpdate.setPrice(acc.getPrice());

        Gson gson = new Gson();
        String jsonRoom = gson.toJson(curAccomUpdate);

        UpdateRoomResult = jsonRoom;

    }

    public void deleteRoom() {
        deletedRoomResult = "";
        Accommodation accom = accommodationFacade.find(Integer.parseInt(deletedAccomId));
        if (accom != null) {
            accommodationFacade.remove(accom);
            deletedRoomResult = "success";
        } else {
            deletedRoomResult = "false";
        }

        // return deletedRoomResult;
    }

    public String displayRoomDetail(int id) {
        try {
            Accommodation accom = accommodationFacade.find(id);
            if (accom != null) {
                curAccom = new Accommodation();
                curAccom.setAccomName(accom.getAccomName());
                curAccom.setDescription(accom.getDescription());
                curAccom.setPrice(accom.getPrice());
                curAccom.setAccomId(id);
                curAccom.setNoOfBed(accom.getNoOfBed());
                curAccom.setNoOfPersons(accom.getNoOfPersons());
                curAccom.setNoOfToilet(accom.getNoOfToilet());
                curAccom.setCustId(customerFacade.find(1));

                List<Comments> commentsCount = commentsFacade.findCommentsByAccomId(id);// != null ? commentsFacade.findCommentsByAccomId(id).size() : 0;
                Gson gson = new Gson();
                RoomImage roomImage = gson.fromJson(accom.getImages(), RoomImage.class);
                // curAccom.setImages(accom.getImages());
                slider1 = roomImage.getSlider1();
                slider2 = roomImage.getSlider2();
                slider3 = roomImage.getSlider3();

                List<Rating> ratings = ratingFacade.findByAccRoomId(id);
                int sumRateScore = 0;
                for (Rating rate : ratings) {
                    sumRateScore += rate.getScore();
                }
                displayRate = ratings.size() > 0 ? sumRateScore / ratings.size() : 0;
            }
        } catch (Exception ex) {
            printStackTrace();
        }
        return "roomdetail";
    }

    public void bookRoom() {
        bookRoomResult = "";
        if (customerBean.getCurCust() == null) {
//            Customer tmp = customerBean.getCurCust();
            bookRoomResult = "requiredlogin";
            return;
        }

        try {

            OrderRoom order = new OrderRoom();
            //    OrderDetail orderDetail = new OrderDetail();
            order.setOrderId(1);
            order.setCustID(curAccom.getCustId());
            order.setTotalPrice(new BigDecimal(curAccom.getPrice(), MathContext.DECIMAL64));
            order.setOrderDate(new Date());
            order.setStatus("Đã đặt phòng");
            order1Facade.create(order);
            bookRoomResult = "success";

        } catch (Exception ex) {
            printStackTrace();
            bookRoomResult = "false";
        }
    }

    public String createRoom() {
        try {
//             log.info("call upload...");      
//        log.log(Level.INFO, "content-type:{0}", file.getContentType());
//        log.log(Level.INFO, "filename:{0}", file.getName());
//        log.log(Level.INFO, "submitted filename:{0}", file.getSubmittedFileName());

            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            roomImageFileNames = new ArrayList<>();
            List<Part> files = new ArrayList<Part>();

            files.add(thumbnail);

            files.add(file1);

            files.add(file2);

            files.add(file3);
            // Uploading room image 
            for (Part itemFile : files) {

                if (itemFile == null) {

                    continue;
                }

                InputStream inputStream = itemFile.getInputStream();
                String fileName = dateFormat.format(date) + getFilename(itemFile);
                roomImageFileNames.add(fileName);
                File file = new File("C:/room4u/images/" + fileName);
                FileOutputStream outputStream = new FileOutputStream(file);

                if (!file.exists()) {
                    file.createNewFile();
                }

                byte[] buffer = new byte[6096];
                int bytesRead = 0;
                while (true) {
                    bytesRead = inputStream.read(buffer);
                    if (bytesRead > 0) {
                        outputStream.write(buffer, 0, bytesRead);
                    } else {
                        break;
                    }
                }
                outputStream.close();
                inputStream.close();
            }
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Room is created!"));

            room.setAccomId(1);
            Customer cust = customerFacade.find(1);
            room.setCustId(cust);
            room.setAddress(houseNumber
                    + " " + street + " " + ward + " " + district + " " + city);
            room.setCreatedDate(date);

            room.setCreatedBy("Can");

            // Store Image File name as json string.
            RoomImage roomImage = new RoomImage();

            roomImage.setThumbnail(roomImageFileNames.get(0));
            roomImage.setSlider1(roomImageFileNames.get(1));
            roomImage.setSlider2(roomImageFileNames.get(2));
            roomImage.setSlider3(roomImageFileNames.get(3));

            Gson gson = new Gson();
            String jsonImage = gson.toJson(roomImage);
            room.setImages(jsonImage);

            accommodationFacade.create(room);
            //RequestContext.getCurrentInstance().execute("alert('peek-a-boo');");

        } catch (Exception ex) {
            printStackTrace();
        }

        return "index";
    }

    public void roomRating() {
        //  FacesContext context = FacesContext.getCurrentInstance();
        //context.addMessage(null, new FacesMessage("Successful", "Your message: "));
//        context.addMessage(null, new FacesMessage("Second Message", "Additional Message Detail"));
        try {
            Rating rate = new Rating();
            rate.setRateId(1);
            rate.setCustId(curAccom.getCustId());
            rate.setAccomId(curAccom);
            rate.setScore(Integer.parseInt(roomRatingSelected));
            ratingFacade.create(rate);
        } catch (Exception ex) {
            printStackTrace();
        }
    }

// Get File Name when upload file
    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.  
            }
        }
        return null;
    }

}

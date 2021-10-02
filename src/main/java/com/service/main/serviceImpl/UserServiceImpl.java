package com.service.main.serviceImpl;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.service.main.DAO.UserDAO;
import com.service.main.DAO.UserPictureDAO;
import com.service.main.entity.UserEntity;
import com.service.main.entity.UserPictureEntity;
import com.service.main.jwt.JwtUtil;
import com.service.main.model.UserAuthModel;
import com.service.main.model.UserCredentialModel;
import com.service.main.model.UserModel;
import com.service.main.model.UserPersonalInfoModel;
import com.service.main.model.UserPlanModel;
import com.service.main.model.UserProfileInfoModel;
import com.service.main.service.UserService;
import com.service.main.util.RandomIdGenerator;

@Service
public class UserServiceImpl implements UserService {

	@Value("${upload.path}")
	private String uploadPath;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserPictureDAO userPictureDAO;
	
	@Autowired
	private RandomIdGenerator randomIdGenerator;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AuthenticationManager AuthenticationManager;
	
	static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);	
	
	public List<UserModel> getAllUsers() throws Exception {
		
		ArrayList<UserModel> userList = new ArrayList<UserModel>();
		List<UserEntity> userData = userDAO.getAllUsers();
		for (UserEntity userEntity : userData) {
			UserModel userModel = new UserModel();
			userModel.setPhoneNumber(userEntity.getPhoneNumber());
			userModel.setUserId(userEntity.getUserId());
			userModel.setUserStatus(userEntity.getUserStatus());
			userModel.setEmail(userEntity.getEmail());
			userModel.setPassword(userEntity.getPassword());
			userList.add(userModel);
		}
		
		return userList;
	}
	
	public String saveUser(UserModel userModel) throws Exception {
		
		Boolean userIdIndicator = true;
		String randomId;
		String token;
        java.util.Date CurrentDate = new Date();
        Date timeStamp = new java.sql.Timestamp(CurrentDate.getTime());
        
        randomId = randomIdGenerator.randomId();
//        UserEntity userIdCheck = userDAO.getUserId(randomId);
        Optional<UserEntity> checkNull = Optional.ofNullable(userDAO.getUserId(randomId));  
        Optional<UserEntity> isEmailAlreadyPresent = Optional.ofNullable(userDAO.getUserByEmail(userModel.getEmail()));  
        
        if (!isEmailAlreadyPresent.isPresent()) {
			
            do {
            	
            	if (checkNull.isPresent()) {
            		
            		randomId = randomIdGenerator.randomId();
    			}
            	else {
            		
            		UserEntity newUser = new UserEntity();
            		newUser.setUserId(randomId);
            		newUser.setFirstName(userModel.getFirstName());
            		newUser.setLastName(userModel.getLastName());
            		newUser.setCountry(userModel.getCountry());
            		newUser.setPhoneNumber(userModel.getPhoneNumber());
            		newUser.setEmail(userModel.getEmail());
            		newUser.setPassword(userModel.getPassword());
            		newUser.setPlan(userModel.getPlan());
            		newUser.setPlanStatus(1);
            		newUser.setUserStatus(1);
            		newUser.setCre_rec_ts(timeStamp);
            		newUser.setUpd_rec_ts(timeStamp);
            		
            		userDAO.saveUser(newUser);
            		userIdIndicator = false;
            		token = getInitToken(userModel.getEmail(), userModel.getPassword(), randomId);
            		return token;
    			}
        		
    		} while (userIdIndicator);
		}
        
        return "failed";
	}
	
	public String emailVerification(String userModel) throws Exception {
		Optional<UserEntity> isEmailAlreadyPresent = Optional.ofNullable(userDAO.getUserByEmail(userModel)); 
		if (!isEmailAlreadyPresent.isPresent()) {
			return "success";
		}
		return "failed";
	}
	
	public String UsersAuthentication(UserAuthModel userAuthModel) throws Exception {
		
		Optional<UserEntity> userDetails = Optional.ofNullable(userDAO.UserAuthentication(userAuthModel.getEmail(), userAuthModel.getPassword()));  
		
		if (userDetails.isPresent()) {
			if (userAuthModel.getEmail().equals(userDetails.get().getEmail()) && userAuthModel.getPassword().equals(userDetails.get().getPassword())) {
				return userDetails.get().getUserId();
			} else {
				return "failed";
			}
		}
		return "failed";
		
	}
	
	public UserModel getPersonalInfo(String userId) throws Exception {
		
		UserEntity userDetails = userDAO.getUserId(userId);
		Optional<UserEntity> isUserExist = Optional.ofNullable(userDetails);
		UserModel userModel = new UserModel();
		
		if (isUserExist.isPresent()) {
			
    		userModel.setUserId(userDetails.getUserId());
    		userModel.setFirstName(userDetails.getFirstName());
    		userModel.setLastName(userDetails.getLastName());
    		userModel.setCountry(userDetails.getCountry());
    		userModel.setPhoneNumber(userDetails.getPhoneNumber());
    		userModel.setEmail(userDetails.getEmail());
    		userModel.setPassword(userDetails.getPassword());
    		userModel.setPlan(userDetails.getPlan());
    		userModel.setPlanStatus(userDetails.getPlanStatus());
    		userModel.setUserStatus(userDetails.getUserStatus());
			
			return userModel;
		}
		
		return userModel;
	}
	
	public UserProfileInfoModel getProfileInfo(String userId) throws Exception {
		
		UserEntity userDetails = userDAO.getUserId(userId);
		Optional<UserEntity> isUserExist = Optional.ofNullable(userDetails);
		UserProfileInfoModel userModel = new UserProfileInfoModel();
		
		if (isUserExist.isPresent()) {
			
    		userModel.setFirstName(userDetails.getFirstName());
    		userModel.setLastName(userDetails.getLastName());
    		userModel.setEmail(userDetails.getEmail());
			
			return userModel;
		}
		
		return userModel;
	}
	
	public String updateUserPersonalInfo(UserPersonalInfoModel userPersonalInfoModel) throws Exception {
		
		UserEntity userDetails = userDAO.getUserId(userPersonalInfoModel.getUserId());
		Optional<UserEntity> isUserExist = Optional.ofNullable(userDetails);
		
		if (isUserExist.isPresent()) {
			
			userDetails.setFirstName(userPersonalInfoModel.getFirstName());
			userDetails.setLastName(userPersonalInfoModel.getLastName());
			userDetails.setCountry(userPersonalInfoModel.getCountry());
			userDetails.setPhoneNumber(userPersonalInfoModel.getPhoneNumber());
//			userDetails.setEmail(userPersonalInfoModel.getEmail());
			userDAO.saveUser(userDetails);
			
			return "success";
		}
		
		return "failed";
	}
	
	public String updateUserCredential(UserCredentialModel userCredentialModel) throws Exception {
		
		UserEntity userDetails = userDAO.getUserId(userCredentialModel.getUserId());
		Optional<UserEntity> isUserExist = Optional.ofNullable(userDetails);
		
		if (isUserExist.isPresent()) {
			
			if (userCredentialModel.getCurrentPassword().matches(userDetails.getPassword())) {
			
				userDetails.setPassword(userCredentialModel.getPassword());
				userDAO.saveUser(userDetails);
				return "success";
			}
			
			return "NOT_MATCH";
		}
		
		return "failed";
	}
	
	public String updateUserPlan(UserPlanModel userPlanModel) throws Exception {
		
		UserEntity userDetails = userDAO.getUserId(userPlanModel.getUserId());
		Optional<UserEntity> isUserExist = Optional.ofNullable(userDetails);
		
		if (isUserExist.isPresent()) {
			
			userDetails.setPlan(userPlanModel.getPlan());
			userDetails.setPlanStatus(userPlanModel.getPlanStatus());

			userDAO.saveUser(userDetails);
			
			return "success";
		}
		
		return "failed";
	}
	
	public String getUserId(UserAuthModel userAuthModel) throws Exception {
		
		Optional<UserEntity> isUserExist = Optional.ofNullable(userDAO.UserAuthentication(userAuthModel.getEmail(), userAuthModel.getPassword())); 
		
		if (isUserExist.isPresent()) {
			
			return isUserExist.get().getUserId();
		}
		
		return "failed";
	}
	
	public String getToken(UserAuthModel userAuthModel) throws Exception {
		
		String loginUser = getUserId(userAuthModel);
		if (loginUser=="failed") {
			return "failed";
		}
		AuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAuthModel.getEmail(), userAuthModel.getPassword()));
		return jwtUtil.generateToken(userAuthModel.getEmail(), loginUser);
	}
	
	public String getInitToken(String email, String password, String userId) throws Exception {
		
		AuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		return jwtUtil.generateToken(email, userId);
	}
	
	public String getRefreshToken(String tokenParam) throws Exception {

		return jwtUtil.generateRefreshToken(tokenParam);
	}
	
	public void saveUserImage(MultipartFile userImage, String userId) throws Exception {

		String imagePath = null;
		java.util.Date CurrentDate = new Date();
		Date timeStamp = new java.sql.Timestamp(CurrentDate.getTime());
	    LocalDateTime myDateObj = LocalDateTime.now();
	    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH-mm-ss'Z'");
	    String formattedDate = myDateObj.format(myFormatObj);
	    
        // read an image from url
        BufferedImage image = ImageIO.read(convertMultiPartToFile(userImage));

        // resize image to 300x150
//        Image scaledImage = image.getScaledInstance(300, 350, Image.SCALE_DEFAULT);

        // save the resize image aka thumbnail
//        ImageIO.write(convertToBufferedImage(scaledImage), "jpg", new File(uploadPath + userId + ".png"));
        imagePath = userId + "-" + formattedDate + ".jpg";
        logger.info("[UserServiceImpl] [saveUserImage] saving user image path: " + uploadPath + imagePath);
        ImageIO.write(image, "jpg", new File(uploadPath + imagePath));
        
        UserPictureEntity userPic = new UserPictureEntity();
        
        userPic.setUserId(userId);
        userPic.setImagePath(imagePath);
        userPic.setImgStatus(0);
        userPic.setImgInd(0);
        userPic.setCre_rec_ts(timeStamp);
        userPic.setUpd_rec_ts(timeStamp);
        
        userPictureDAO.saveUser(userPic);
        
        saveprofilePicture(userId, imagePath);
	}
	
	public byte[] getUserImage(String userId) throws Exception {

		
		Optional<UserPictureEntity> userImg = Optional.ofNullable(userPictureDAO.getProfilePicture(userId, 1)); 
		
		if (userImg.isPresent()) {
			
			if(userImg.get().getImgStatus()==1) {
				logger.info("[UserServiceImpl] [getUserImage] user image path: " + uploadPath + userId + ".jpg");
				return readImage(userImg.get().getImagePath());
			}
		}
		
 		return null;
	}
	
	public HashMap<String, byte[]> getAllUserImages(String userId) throws Exception {

		HashMap<String, byte[]> imgList = new HashMap<String, byte[]>();
		Optional<List<UserPictureEntity>> userImgList = Optional.ofNullable(userPictureDAO.getAllImages(userId)); 
		
		if (userImgList.isPresent()) {
			
			for (UserPictureEntity userImgEnt : userImgList.get()) {
				if(userImgEnt.getImgStatus()==1) {
					imgList.put(userImgEnt.getImagePath(), readImage(userImgEnt.getImagePath()));
				}

			}
			return imgList;
		}
		
		return null;
	}
	
	public void saveprofilePicture(String userId, String imagePath) throws Exception {
		
		UserPictureEntity oldUserImage = userPictureDAO.getProfilePicture(userId, 1);
		Optional<UserPictureEntity> userImg = Optional.ofNullable(oldUserImage); 
		
		if (userImg.isPresent()) {
			logger.info("[UserServiceImpl] [saveprofilePicture] user old image path: " + uploadPath + oldUserImage.getImagePath());
			File file = new File(uploadPath + oldUserImage.getImagePath());
			file.delete();
			oldUserImage.setImgStatus(0);
//			userPictureDAO.deleteUserImage(oldUserImage.getUserPicIdRec());
		}
		
		UserPictureEntity newUserImage = userPictureDAO.getProfilePicturePath(imagePath);
		Optional<UserPictureEntity> newUserImg = Optional.ofNullable(newUserImage); 
		
		if (newUserImg.isPresent()) {
			logger.info("[UserServiceImpl] [saveprofilePicture] user image path: " + newUserImage.getImagePath());
			newUserImage.setImgStatus(1);
			userPictureDAO.saveUser(newUserImage);
		}
	}
	
	public byte[] readImage(String file) throws Exception {
		logger.info("[UserServiceImpl] [readImage] user image path: " + uploadPath + file);
		BufferedImage image = ImageIO.read(new File(uploadPath + file));
		return toByteArray(image);
	}
	
    // convert Image to BufferedImage
    public static BufferedImage convertToBufferedImage(Image img) {

        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bi = new BufferedImage(
                img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics2D = bi.createGraphics();
        graphics2D.drawImage(img, 0, 0, null);
        graphics2D.dispose();

        return bi;
    }
    
    private File convertMultiPartToFile(MultipartFile file ) throws Exception
    {
        File convFile = new File( file.getOriginalFilename() );
        FileOutputStream fos = new FileOutputStream( convFile );
        fos.write( file.getBytes() );
        fos.close();
        return convFile;
    }
    
    public static byte[] toByteArray(BufferedImage bi)
            throws Exception {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            return bytes;

        }

}

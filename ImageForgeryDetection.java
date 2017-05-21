 Mat gray_image;
cvtColor( image, gray_image, CV_BGR2GRAY );

src = imread( "location:\\sampleImage.jpg" );
//horizontal convolution, h is the image after horizontal convolution.
filter2D(src, h, -1, &filter1, Point(-1,-1), 5.0, BORDER_REPLICATE);
//vertical convolution, v is the image after vertical convolution.
filter2D(src, v, -1, &filter, Point(-1,-1), 5.0, BORDER_REPLICATE);

Mat A=Mat(h.rows,h.cols,CV_32FC3);
Mat B=Mat(v.rows,v.cols,CV_32FC3);
Mat Gradient=Mat(v.rows,v.cols,CV_32FC3);
Mat Theta=Mat(v.rows,v.cols,CV_32FC3);
float val = (v.rows-2)*(v.cols-2);
Mat arr=Mat(val,4,CV_32FC3);
Mat Edge=Mat((v.rows-2),(v.cols-2),CV_32FC3);
 
for(i=0; i<v.rows;i++) {  
  for(j=0; j<v.cols; j++) {          
    if(B.at<float>(i,j) == 0) {
      //adding a negligible value
      param = ((A.at<float>(i,j))/((B.at<float>(i,j)) + 0.00001));
      //converting to the range [0,2PI]
      var= atan2(A.at<float>(i,j) , B.at<float>(i,j) + 0.00001 );                        
      Gradient.at<float>(i,j)= (var*(180/PI)) + (var > 0 ? 0 : 360);//radians to degree
    }
   else {  
    param = ((A.at<float>(i,j))/(B.at<float>(i,j)));
    var= atan2(A.at<float>(i,j) , B.at<float>(i,j) );
    Gradient.at<float>(i,j)= (var*(180/PI)) + (var > 0 ? 0 : 360);//radians to degree
   }
  }
}

for(i=0; i<v.rows; i++){ 
  for(j=0; j<v.cols; j++){
    if( ((22.5) <= (Gradient.at<float>(i,j)) ) && ((Gradient.at<float>(i,j)) < ((3)*(22.5)) ) ) {
      Theta.at<float>(i,j) = (2)*(22.5);
    }else if( ((3*22.5) <= (Gradient.at<float>(i,j)) ) && ((Gradient.at<float>(i,j)) < ((5)*(22.5)) ) ) {
      Theta.at<float>(i,j) = (4)*(22.5);
    }else if( ((5*22.5) <= (Gradient.at<float>(i,j)) ) && ((Gradient.at<float>(i,j)) < ((7)*(22.5)) ) ) {
      Theta.at<float>(i,j) = (6)*(22.5);
    }else if( ((7*22.5) <= (Gradient.at<float>(i,j)) ) && ((Gradient.at<float>(i,j)) < ((9)*(22.5)) ) ) { 
      Theta.at<float>(i,j) = (8)*(22.5);
    }else if( ((9*22.5) <= (Gradient.at<float>(i,j)) ) && ((Gradient.at<float>(i,j)) < ((11)*(22.5)) ) ) {
      Theta.at<float>(i,j) = (10)*(22.5);
    }else if( ((11*22.5) <= (Gradient.at<float>(i,j)) ) && ((Gradient.at<float>(i,j)) < ((13)*(22.5)) ) ) {
      Theta.at<float>(i,j) = (12)*(22.5);
    }else if( ((13*22.5) <= (Gradient.at<float>(i,j)) ) && ((Gradient.at<float>(i,j)) < ((15)*(22.5)) ) ) { 
      Theta.at<float>(i,j) = (14)*(22.5);
    }else {
      Theta.at<float>(i,j) = 0;
    } 
  } 
}
for(i=1; i<(Theta.rows-1);i++){
  for(j=1; j<(Theta.cols-1);j++){
    //zone1
    if( Theta.at<float>(i,j) == 0.0) { 
      arr.at<float>(a,0) = 1;
      arr.at<float>(a,1) = highodd(im_gray,i,j);
      arr.at<float>(a,2) = medodd(im_gray,i,j);
      arr.at<float>(a,3) = lowodd(im_gray,i,j);
      a++;
    }
    //zone2
    else if( Theta.at<float>(i,j) == 45){ 
      arr.at<float>(a,0) = 2;
      arr.at<float>(a,1) = higheven(im_gray,i,j);
      arr.at<float>(a,2) = medeven(im_gray,i,j);
      arr.at<float>(a,3) = loweven(im_gray,i,j);
      a++;  
    }
  }
}
void cvFloodFill(CvArr* image, CvPoint seed_point, CvScalar new_val, CvScalar lo_diff=cvScalarAll(0), 
   CvScalar up_diff=cvScalarAll(0), CvConnectedComp* comp=NULL, int flags=4, CvArr* mask=NULL )

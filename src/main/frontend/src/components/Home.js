import React, { useState } from "react";
import {
    MDBCard,
    MDBCardBody,
    MDBCardTitle,
    MDBCardText,
    MDBCardImage,
    MDBBtn,
    MDBContainer,
    MDBRow,
    MDBCol
} from 'mdb-react-ui-kit';

const Home = () => {

    const [books, setBooks] = useState([]);
    const [title, setTitle] = useState('');
    const [error, setError] = useState(null);

    

    const searchTitle = async () => {
        if (!title) {
            setError('제목을 입력하여 주십시오');
            return;
        }

        const response = await fetch(`http://localhost:8080/books/list?title=${title}`);

        if (response.ok) {
            const data = await response.json();
            setBooks(data);
            setError(null);
        } else {
            const errorData = await response.json();
            setError(`Failed to search books: ${errorData.message}`);
        }
    }
    

    return (
        <div className="container text-center">
            <h1 className="mb-3">It Book</h1>
            <div className="col-md-4 mx-auto mb-3">
                <div className="input-group">
                    <div className="form-outline" data-mdb-input-init>
                        <input type="search" id="title" onChange={(e) => setTitle(e.target.value)} className="form-control" />
                        <label className="form-label" htmlFor="title">제목 검색</label>
                    </div>
                    <button type="button" className="btn btn-primary" data-mdb-ripple-init onClick={searchTitle}>
                        <i className="fas fa-search"></i>
                    </button>
                </div>
                {error && <div className="alert alert-danger">{error}</div>}
            </div>
            <MDBContainer>
                <MDBRow>
                    {books.map(book => (
                        <MDBCol md="4" key={book.id}>
                        <MDBCard>
                            <MDBCardImage src={`https://contents.kyobobook.co.kr/sih/fit-in/458x0/pdt/${book.isbn}.jpg`} position='top' alt='...' />
                            <MDBCardBody>
                                <MDBCardTitle>{book.title}</MDBCardTitle>
                                <MDBCardText>
                                    {book.price}원
                                </MDBCardText>
                                <MDBBtn href={`https://product.kyobobook.co.kr/detail/${book.sale_id}`}>구입하기</MDBBtn>
                            </MDBCardBody>
                        </MDBCard>
                    </MDBCol>
                    ))}
                    
                    
                </MDBRow>
                

            </MDBContainer>

        </div>
    )
}

export default Home;
